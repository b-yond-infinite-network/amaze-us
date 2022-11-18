from datetime import datetime, date, timedelta

from src.model import Bus, Schedule


class BusService(object):
    async def get_with_schedules_by_week(self, bus_id: int, day_of_the_week: date):
        first_day_of_the_week = day_of_the_week - \
            timedelta(days=day_of_the_week.weekday() + 1)
        last_day_of_the_week = first_day_of_the_week + timedelta(days=7)

        bus = await Bus.objects.get_or_none(id=bus_id)
        bus.schedules = await Schedule.objects.filter(driver=bus, begin__gte=first_day_of_the_week, end__lt=last_day_of_the_week).select_related(Schedule.driver).all()

        return bus
