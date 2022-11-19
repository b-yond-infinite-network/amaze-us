import os
import smtplib
from email.message import EmailMessage

def send_message(message: EmailMessage):
    with smtplib.SMTP(os.getenv('SMTP_HOST'), int(os.getenv('SMTP_PORT'))) as smtp:
        smtp.send_message(message)
