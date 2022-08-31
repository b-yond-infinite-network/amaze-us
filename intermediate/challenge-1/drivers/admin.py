from django.contrib import admin
from .models import Driver_Information
# Register your models here.

class show_drivers(admin.ModelAdmin):
    list_display = ['First_Name', 'Last_Name', 'Social_Security_Number', 'Email']


admin.site.register(Driver_Information, show_drivers)
