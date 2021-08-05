from rest_framework.response import responses
from rest_framework.decorators import api_view
from rest_framework import viewsets
from rest_framework.serializers import Serializer
from .models import (
    Bus,
    Driver,
    Shift
)

from .serializers import (
    BusSerializer,
    DriverSerializer,
    ShiftSerializer
)


class BusViewSet(viewsets.ModelViewSet):
    serializer_class = BusSerializer
    queryset = Bus.objects.all()


class DriverViewSet(viewsets.ModelViewSet):
    serializer_class = DriverSerializer
    queryset = Driver.objects.all()


class ShiftViewSet(viewsets.ModelViewSet):
    serializer_class = ShiftSerializer
    queryset = Shift.objects.all()


# Registeration

@api_view(['POST', ])
def registeration_view(request):
    if request.method == 'POST':
        serializer = RegisterationSerializer(data=request.data)
        data = {}
        if serializer.is_valid():
            account = serializer.save()
