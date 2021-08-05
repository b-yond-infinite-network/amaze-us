from django.contrib import admin
from .models import (Bus, Driver, Shift)

# Register your models here.


admin.site.register(Bus)
admin.site.register(Driver)
admin.site.register(Shift)
