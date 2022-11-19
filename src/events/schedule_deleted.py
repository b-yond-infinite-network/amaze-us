import json

from email.message import EmailMessage

from fastapi_events.handlers.local import local_handler

from src.infrastructure import send_message

ScheduleDeletedEvent = "ScheduleDeletedEvent"


@local_handler.register(event_name=ScheduleDeletedEvent)
async def schedule_deleted_handler(event) -> None:
    schedule = event[1]["schedule"]

    driver = schedule["driver"]
    msg = EmailMessage()
    msg["Subject"] = "Schedule deleted"
    msg["From"] = "Hedwig"
    msg["To"] = driver.email
    msg.set_content(f"Hello {driver.first_name} {driver.last_name}, your schedule that starts at {schedule['begin']} and ends at {schedule['end']} was deleted.")

    send_message(message=msg)
