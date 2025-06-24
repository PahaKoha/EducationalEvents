from __future__ import annotations

from logging.config import dictConfig

LOGGING = {
    "disable_existing_loggers": False,
    "formatters": {
        "verbose": {
            "format": "[%(levelname)s | %(asctime)s] %(module)s : %(message)s",
        },
    },
    "handlers": {
        "console": {
            "level": "DEBUG",
            "class": "logging.StreamHandler",
            "formatter": "simple",
        },
    },
    "loggers": {
        "celery": {
            "handlers": ["console"],
            "level": "DEBUG",
            "propagate": True,
        },
    },
}


def setup_logging():
    dictConfig(LOGGING)
