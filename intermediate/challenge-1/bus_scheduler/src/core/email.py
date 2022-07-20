from typing import List
from fastapi_mail import FastMail, MessageSchema, ConnectionConfig
from pydantic import EmailStr
from src.config import settings
from src.database.db import ActiveSession, get_session

from src.models import User, Schedule
from src.templates.email_templates import get_email_template
from src.services.driver_service import driver as driver_service

STRING_DATE_FORMAT = "%d/%m/%Y"
STRING_TIME_FORMAT = "%H:%M"


async def send_mail(email: List[EmailStr], subject: str, template: str):
    email = [email] if isinstance(email, List) else [email]
    conf = ConnectionConfig(
        MAIL_USERNAME=settings.email.EMAIL_USERNAME,
        MAIL_PASSWORD=settings.email.EMAIL_PASSWORD,
        MAIL_FROM=settings.email.EMAIL_FROM,
        MAIL_PORT=settings.email.EMAIL_PORT,
        MAIL_SERVER=settings.email.EMAIL_HOST,
        MAIL_TLS=True,
        MAIL_SSL=False,
        USE_CREDENTIALS=True,
        VALIDATE_CERTS=True
    )

    message = MessageSchema(
        subject=subject,
        recipients=email,
        body=template,
        subtype="html"
    )

    fm = FastMail(conf)
    await fm.send_message(message)


async def send_registration_email(user: User):
    subject = "Registration Confirmed"
    data = user.__dict__
    template = get_email_template("new_user.html", data=data)

    await send_mail(email=user.email, subject=subject, template=template)


async def send_new_schedule_email(schedule: Schedule):
    email, subject, template = get_schedule_email_data(new_schedule=schedule)
    await send_mail(email=email, subject=subject, template=template)


async def send_update_schedule_email(original_data: dict, new_schedule: Schedule):
    email, subject, template = get_schedule_email_data(original_data=original_data, new_schedule=new_schedule)
    await send_mail(email=email, subject=subject, template=template)


def get_schedule_email_data(new_schedule: Schedule, original_data: dict = None):
    db: ActiveSession = next(get_session())
    driver = driver_service.get(db_session=db, id=new_schedule.driver_id)
    if original_data and original_data["original_driver_id"] == new_schedule.driver_id:
        subject = "Your Schedule was updated!"
        data = {
            "first_name": driver.first_name,
            "old_date": original_data['original_day'].strftime(STRING_DATE_FORMAT),
            "old_start": original_data['original_start_hour'].strftime(STRING_TIME_FORMAT),
            "old_end": original_data['original_end_hour'].strftime(STRING_TIME_FORMAT),
            "new_date": new_schedule.day.strftime(STRING_DATE_FORMAT),
            "new_start": new_schedule.start_hour.strftime(STRING_TIME_FORMAT),
            "new_end": new_schedule.end_hour.strftime(STRING_TIME_FORMAT)
        }

        template = get_email_template(template_name="update_schedule.html", data=data)
    else:
        subject = "New Schedule Created"
        data = {
            "first_name": driver.first_name,
            "day": new_schedule.day.strftime(STRING_DATE_FORMAT),
            "start": new_schedule.start_hour.strftime(STRING_TIME_FORMAT),
            "end": new_schedule.end_hour.strftime(STRING_TIME_FORMAT),
        }

        template = get_email_template("new_schedule.html", data=data)

    return driver.email, subject, template
