from pydantic import BaseModel, Field, SecretStr, field_validator


class EmailConfig(BaseModel):
    host: str = Field(..., description="SMTP хост")
    port: int = Field(587, description="Порт SMTP сервера")
    username: str = Field(..., description="Имя пользователя")
    password: SecretStr = Field(..., description="Пароль или токен")
    use_tls: bool = Field(True, description="Использовать TLS")
    timeout: int = Field(10, description="Таймаут подключения в секундах")

    @field_validator("port")
    def validate_port(cls, value: int) -> int:
        if not 1 <= value <= 65535:
            raise ValueError("Некорректный порт SMTP")
        return value
