from django.shortcuts import render
from bus.models import Bus_Information
from drivers.models import Driver_Information
from .models import Bus_Drivers_Schedule
# Create your views here.


def index(request):
    all_bus_qty = Bus_Information.objects.all().count()
    all_driver_qty = Driver_Information.objects.all().count()
    context={'all_bus_qty':all_bus_qty, 'all_driver_qty':all_driver_qty}
    return render(request, "index.html", context)

def all_bus(request):
    var_all_bus = Bus_Information.objects.all()
    context={'var_all_bus':var_all_bus}
    return render(request, "all_bus.html", context)

def all_driver(request):
    var_all_drivers = Driver_Information.objects.all()
    context={'var_all_drivers':var_all_drivers}
    return render(request, "all_drivers.html", context)

def bus_schedule(request, pk):
    get_bus = Bus_Information.objects.get(id=pk)
    this_bus_schedule = Bus_Drivers_Schedule.objects.filter(Bus=get_bus)
    context={'this_bus_schedule':this_bus_schedule, 'get_bus':get_bus}
    return render(request, "Bus_Schedule.html", context)


def driver_schedule(request, pk):
    get_driver = Driver_Information.objects.get(id=pk)
    this_driver_schedule = Bus_Drivers_Schedule.objects.filter(Driver=get_driver)
    context={'this_driver_schedule':this_driver_schedule, 'get_driver':get_driver}
    return render(request, "Driver_Schedule.html", context)