import logging
import smtplib
import ssl
from email.mime.text import MIMEText
from email.utils import formatdate
from typing import List

from sender.message import EVENT_TEMPLATE, REGISTRATION_TEMPLATE, MessageSubject, MessageType
from sender.server import EmailConfig


class EmailSender:
    def __init__(self, config: EmailConfig) -> None:
        self._config = config
        self._logger = logging.getLogger(__name__)

    def send_email(self, rabbit_message: dict) -> bool:
        messages_type = MessageType(rabbit_message["type"])

        messages: List[MIMEText] = list()

        if messages_type == MessageType.PortalRegistration:
            username = rabbit_message["username"]
            mail = rabbit_message["mail"]
            body = REGISTRATION_TEMPLATE.format(username=username)
            msg = self._build_email(
                subject=MessageSubject.Registration,
                body=body,
                receiver=mail,
            )

            messages.append(msg)

        elif messages_type == MessageType.NewEventInfo:
            receivers = rabbit_message["receivers"]

            event = rabbit_message["event"]
            event_title = event["title"]
            event_link = event["link"]

            for receiver in receivers:
                username = receiver["username"]
                mail = receiver["mail"]
                body = EVENT_TEMPLATE.format(username=username, event_title=event_title, event_link=event_link)
                msg = self._build_email(
                    subject=MessageSubject.NewEvent,
                    body=body,
                    receiver=mail,
                )

                messages.append(msg)

        else:
            self._logger.error(f"Unknown messages type: {messages_type}")
            return False

        return self._send_via_smtp(messages_to_send=messages)

    def _send_via_smtp(self, messages_to_send: List[MIMEText]) -> bool:
        def _send_message(final_server: smtplib.SMTP_SSL | smtplib.SMTP, messages: List[MIMEText]) -> None:
            for msg in messages:
                final_server.send_message(msg)

        try:
            context = ssl.create_default_context()

            if self._config.port == 465:
                with smtplib.SMTP_SSL(
                    host=self._config.host, port=self._config.port, context=context, timeout=self._config.timeout
                ) as server:
                    server.login(user=self._config.username, password=self._config.password.get_secret_value())
                    _send_message(server, messages_to_send)

            else:
                with smtplib.SMTP(
                    host=self._config.host, port=self._config.port, timeout=self._config.timeout
                ) as server:
                    if self._config.use_tls:
                        server.starttls(context=context)
                    server.login(user=self._config.username, password=self._config.password.get_secret_value())
                    _send_message(server, messages_to_send)

            return True

        except smtplib.SMTPException as e:
            self._logger.error(f"SMTP ошибка: {e}")
            return False
        except TimeoutError:
            self._logger.error("Таймаут подключения к SMTP серверу")
            return False
        except Exception as e:
            self._logger.error(f"Неизвестная ошибка: {e}")
            return False

    def _build_email(self, subject: MessageSubject, body: str, receiver: str) -> MIMEText:
        msg = MIMEText(body, "plain", "utf-8")

        msg["From"] = self._config.username
        msg["To"] = receiver
        msg["Subject"] = subject
        msg["Date"] = formatdate(localtime=True)

        return msg
