from django.contrib import admin
from .models import Bus_Information
# Register your models here.

class show_bus(admin.ModelAdmin):
    list_display = ['model', 'capacity', 'make']


admin.site.register(Bus_Information, show_bus)
