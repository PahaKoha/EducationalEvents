from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    RABBITMQ_URL: str
    RABBITMQ_MESSAGE_QUEUE: str

    SMTP_PORT: int
    SMTP_HOST: str
    MAIL_BOX: str
    MAIL_PASSWORD: str


settings = Settings()
