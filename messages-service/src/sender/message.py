from enum import Enum


class MessageType(str, Enum):
    PortalRegistration = "registration"
    NewEventInfo = "new_event"


class MessageSubject(str, Enum):
    Registration = "Регистрация на портале EventsITMO"
    NewEvent = "Новое мероприятие на EventsITMO"


REGISTRATION_TEMPLATE = """
Уважаемый(ая) {username},

Поздравляем с успешной регистрацией на нашем портале мероприятий!

С уважением,
Команда портала мероприятий ИТМО
"""

EVENT_TEMPLATE = """
Уважаемый(ая) {username},

Кажется, у нас есть кое-что интересное для вас!
Новое мероприятие {title}: {link}

С уважением,
Команда портала мероприятий ИТМО
"""
