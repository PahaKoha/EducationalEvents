from __future__ import annotations

from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    RABBITMQ_URL: str
    RABBITMQ_MESSAGE_QUEUE: str


settings = Settings()
