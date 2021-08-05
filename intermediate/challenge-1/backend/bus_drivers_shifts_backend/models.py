from django.db import models
from .utils import exceptions
from django.db.models.fields import DateField
from django.conf import settings
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token


class CustomManager(models.Manager):

    def update_or_create(self, defaults=None, **kwargs):
        try:
            return super().update_or_create(defaults, **kwargs)
        except Exception:
            raise exceptions.updateOrCreateError(self.model.repr)

    def get(self, *args, **kwargs):
        try:
            return super().get(*args, **kwargs)
        except Exception:
            raise exceptions.doesntExist(self.model.repr)

    def create(self, **kwargs):
        try:
            return super().create(**kwargs)
        except Exception as e:
            e

    def update(self, **kwargs):
        try:
            return super().update(**kwargs)
        except Exception:
            raise exceptions.updateError(self.model.repr)

    def bulk_create(self, objs, batch_size=None):
        try:
            return super().bulk_create(objs, batch_size)
        except Exception:
            raise exceptions.createError(self.model.repr)


class AbstractModel(models.Model):
    objects = CustomManager()
    created_at = models.DateTimeField(auto_now_add=True, null=True)

    class Meta:
        abstract = True


class Driver(AbstractModel):
    first_name = models.CharField(max_length=32)
    last_name = models.CharField(max_length=32)
    ssn = models.IntegerField(unique=True)

    def __str__(self):
        return self.first_name + " " + self.last_name


class Bus(AbstractModel):
    capacity = models.IntegerField(default=0)
    model = models.CharField(max_length=32, null=True)
    make = models.CharField(max_length=32, null=True)

    def __str__(self):
        return self.make + " " + self.model


class Shift(AbstractModel):
    bus = models.ForeignKey(
        Bus, on_delete=models.CASCADE, related_name='shifts')
    driver = models.ForeignKey(
        Driver, on_delete=models.CASCADE, related_name='shifts')
    date = DateField()
    start_at = models.TimeField(auto_now=False, auto_now_add=False)
    end_at = models.TimeField(auto_now=False, auto_now_add=False)

    class Meta:
        unique_together = ('bus', 'driver', 'date', 'start_at')
