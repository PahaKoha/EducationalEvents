import logging

from configs.base_config import settings
from configs.logger import setup_logging
from rabbit.consumer import RabbitConsumer
from sender.sender import EmailSender
from sender.server import EmailConfig

logger = logging.getLogger(__name__)


if __name__ == "__main__":
    config = EmailConfig(
        host=settings.SMTP_HOST,
        port=settings.SMTP_PORT,
        username=settings.MAIL_BOX,
        password=settings.MAIL_PASSWORD,
        use_tls=settings.USE_TLS,
        timeout=settings.TIMEOUT,
    )

    sender = EmailSender(config=config)

    rabbit_consumer = RabbitConsumer(
        rabbit_url=settings.RABBITMQ_URL,
        message_queue=settings.RABBITMQ_MESSAGE_QUEUE,
        sender=sender,
    )

    setup_logging()

    logger.info("Успешно сконфигурировались")

    rabbit_consumer.run()
