import logging
import smtplib
import ssl
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.utils import formatdate

from pydantic import SecretStr, BaseModel, Field, field_validator


FIRST_MAIL = """
<h2>Привет, сынок<h2/>
<h1>Отдай денег и <b>найди мне Лерку<b/><h1/>
"""

SECOND_MAIL = """
<h1>Соси соси ебанушечка<h1/>
"""


class EmailConfig(BaseModel):
    host: str = Field(..., description="SMTP хост")
    port: int = Field(587, description="Порт SMTP сервера")
    username: str = Field(..., description="Имя пользователя")
    password: str = Field(..., description="Пароль или токен")
    use_tls: bool = Field(True, description="Использовать TLS")
    timeout: int = Field(10, description="Таймаут подключения в секундах")

    @field_validator("port")
    def validate_port(cls, value: int) -> int:
        if not 1 <= value <= 65535:
            raise ValueError("Некорректный порт SMTP")
        return value


class EmailSender:
    def __init__(self, config: EmailConfig) -> None:
        self._config = config
        self._logger = logging.getLogger(__name__)

    def send_email(self, subject: str, body: str, receiver: str) -> bool:
        msg = self._build_email(subject, body, receiver)

        return self._send_via_smtp(msg, receiver)

    def _build_email(self, subject: str, body: str, receiver: str) -> MIMEMultipart:
        msg = MIMEMultipart()
        msg["From"] = self._config.username
        msg["To"] = receiver
        msg["Subject"] = subject
        msg["Date"] = formatdate(localtime=True)

        content = MIMEMultipart("alternative")

        text_part = MIMEText("Email requires HTML support", "plain", "utf-8")
        content.attach(text_part)

        html_part = MIMEText(body, "html", "utf-8")
        content.attach(html_part)

        msg.attach(content)
        return msg

    def _send_via_smtp(self, msg: MIMEMultipart, recipient: str) -> bool:
        try:
            context = ssl.create_default_context()

            if self._config.port == 465:
                with smtplib.SMTP_SSL(
                    host=self._config.host, port=self._config.port, context=context, timeout=self._config.timeout
                ) as server:
                    server.login(user=self._config.username, password=self._config.password)
                    server.sendmail(from_addr=self._config.username, to_addrs=recipient, msg=msg.as_string())
            else:
                with smtplib.SMTP(
                    host=self._config.host, port=self._config.port, timeout=self._config.timeout
                ) as server:
                    if self._config.use_tls:
                        server.starttls(context=context)
                    server.login(user=self._config.username, password=self._config.password)
                    server.sendmail(from_addr=self._config.username, to_addrs=recipient, msg=msg.as_string())

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


if __name__ == "__main__":
    sender = EmailSender(
        EmailConfig(
            host="smtp.gmail.com",
            port=587,
            username="educational.events.itmo@gmail.com",
            password="gwlmhhuciujzpbwv",
            use_tls=True,
            timeout=15,
        )
    )

    success = sender.send_email(
        subject="Важное тестовое письмо",
        body=FIRST_MAIL,
        receiver="artem2004920@gmail.com",
    )

    # uletayu_na_gaiti

# pashagerasimik@mail.ru