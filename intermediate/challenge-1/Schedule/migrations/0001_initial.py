# Generated by Django 3.2 on 2022-08-19 07:50

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('drivers', '0001_initial'),
        ('bus', '0002_bus_information_bus_image'),
    ]

    operations = [
        migrations.CreateModel(
            name='Bus_Drivers_Schedule',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('Day', models.CharField(choices=[('Sat', 'Sat'), ('Sun', 'Sun'), ('Mon', 'Mon'), ('Tues', 'Tues'), ('Wed', 'Wed'), ('Thurs', 'Thurs'), ('Fri', 'Fri')], default='', max_length=20)),
                ('from_time', models.TimeField()),
                ('to_time', models.TimeField()),
                ('Bus', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='bus.bus_information')),
                ('Driver', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='drivers.driver_information')),
            ],
            options={
                'verbose_name_plural': 'Bus Drivers Schedule',
            },
        ),
    ]
