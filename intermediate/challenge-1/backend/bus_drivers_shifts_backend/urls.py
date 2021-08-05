from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import (
    BusViewSet,
    DriverViewSet,
    ShiftViewSet
)

router = DefaultRouter()

router.register(r'bus', BusViewSet, basename="buses")
router.register(r'drivers', DriverViewSet, basename="drivers")
router.register(r'shift', ShiftViewSet, basename="shifts")

urlpatterns = [
    path('v1/', include(router.urls))
]
