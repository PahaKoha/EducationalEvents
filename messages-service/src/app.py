from configs.base_config import settings
from sender.server import EmailConfig

if __name__ == "__main__":
    config = EmailConfig(
        host=settings.SMTP_HOST,
        port=settings.SMTP_PORT,
        username=settings.SMTP_USERNAME,
        password=settings.SMTP_PASSWORD,
    )
