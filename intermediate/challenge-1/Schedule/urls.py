from django.urls import path
from . import views

urlpatterns = [
    path('', views.index, name="index"),
    path('all_bus', views.all_bus, name="all_bus"),
    path('all_driver', views.all_driver, name="all_driver"),
    path('bus_schedule/<int:pk>', views.bus_schedule, name="bus_schedule"),
    path('driver_schedule/<int:pk>', views.driver_schedule, name="driver_schedule"),
]