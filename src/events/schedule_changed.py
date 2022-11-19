import json

from email.message import EmailMessage

from deepdiff import DeepDiff
from fastapi_events.handlers.local import local_handler
from fastapi_events.typing import Event

from src.infrastructure import send_message

ScheduleChangedEvent="ScheduleChangedEvent"

@local_handler.register(event_name=ScheduleChangedEvent)
async def schedule_changed_handler(event) -> None:
    old, new = event[1]["old"], event[1]["new"]

    diff = DeepDiff(old, new, ignore_order=True)
    if not diff:
        return

    msg = EmailMessage()
    msg["Subject"] = "Schedule changed"
    msg["From"] = "Hedwig"
    msg["To"] = new['driver'].email
    pretty_diff = json.dumps(diff, indent=4)
    msg.set_content(f"Schedule changed:\n {pretty_diff}")

    send_message(message=msg)