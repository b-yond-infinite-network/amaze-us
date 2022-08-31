from django.db import models

# Create your models here.


class Bus_Information(models.Model):
    class Meta:
        verbose_name_plural = 'Bus Information'

    model = models.CharField(max_length=255)
    capacity = models.CharField(max_length=255)
    make  = models.CharField(max_length=255)
    bus_image = models.ImageField(upload_to='Bus_Image/', default=None, blank=True, null=True)

    def __str__(self):
        return self.model + ', capacity-'+ self.capacity