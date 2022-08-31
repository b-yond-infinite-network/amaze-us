from django.contrib import admin
from .models import Bus_Drivers_Schedule
# Register your models here.


class show_schedule(admin.ModelAdmin):
    list_display = ['Bus', 'Driver', 'Day', 'from_time', 'to_time']


admin.site.register(Bus_Drivers_Schedule, show_schedule)