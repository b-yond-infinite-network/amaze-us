from django.db import models
from bus.models import Bus_Information
from drivers.models import Driver_Information
# Create your models here.


class Bus_Drivers_Schedule(models.Model):
    class Meta:
        verbose_name_plural = 'Bus Drivers Schedule'

    Bus = models.ForeignKey(Bus_Information, on_delete = models.CASCADE)
    Driver = models.ForeignKey(Driver_Information, on_delete = models.CASCADE)
    a = (
        ("Sat", "Sat"),
        ("Sun", "Sun"),
        ("Mon", "Mon"),
        ("Tues", "Tues"),
        ("Wed", "Wed"),
        ("Thurs", "Thurs"),
        ("Fri", "Fri"),
    )
    Day = models.CharField(max_length=20, choices=a, default="")
    b = (
        ("1 PM", "1 PM"),
        ("2 PM", "2 PM"),
        ("3 PM", "3 PM"),
        ("4 PM", "4 PM"),
        ("5 PM", "5 PM"),
        ("6 PM", "6 PM"),
        ("7 PM", "7 PM"),
        ("8 PM", "8 PM"),
        ("9 PM", "9 PM"),
        ("10 PM", "10 PM"),
        ("11 PM", "11 PM"),
        ("12 PM", "12 PM"),
        ("1 AM", "1 AM"),
        ("2 AM", "2 AM"),
        ("3 AM", "3 AM"),
        ("4 AM", "4 AM"),
        ("5 AM", "5 AM"),
        ("6 AM", "6 AM"),
        ("7 AM", "7 AM"),
        ("8 AM", "8 AM"),
        ("9 AM", "9 AM"),
        ("10 AM", "10 AM"),
        ("11 AM", "11 AM"),
        ("12 AM", "12 AM"),
    )
    from_time = models.CharField(max_length=20, choices=b, default="")
    to_time = models.CharField(max_length=20, choices=b, default="")

    def __str__(self):
        return self.from_time + " - "+self.to_time + " - "+self.Day+ " - "+str(self.Bus)+ " - "+str(self.Driver)
