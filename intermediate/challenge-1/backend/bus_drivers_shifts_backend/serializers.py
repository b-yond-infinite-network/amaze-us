from django.db import models
from rest_framework import serializers as rest_serializers
from rest_framework.exceptions import MethodNotAllowed

from .models import (
    Bus,
    Driver,
    Shift
)

names = []


class BusSerializer(rest_serializers.ModelSerializer):
    class Meta:
        model = Bus
        fields = '__all__'


class DriverSerializer(rest_serializers.ModelSerializer):
    class Meta:
        model = Driver
        fields = '__all__'


class ShiftSerializer(rest_serializers.ModelSerializer):
    class Meta:
        model = Shift
        fields = '__all__'
