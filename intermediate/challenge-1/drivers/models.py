from django.db import models

# Create your models here.


class Driver_Information(models.Model):
    class Meta:
        verbose_name_plural = 'Divers Information'
    First_Name = models.CharField(max_length=255)
    Last_Name = models.CharField(max_length=255)
    Social_Security_Number  = models.CharField(max_length=255)
    Email  = models.CharField(max_length=255)

    def __str__(self):
        return self.First_Name + ' '+ self.Last_Name