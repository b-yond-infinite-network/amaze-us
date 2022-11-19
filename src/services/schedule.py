import datetime
from copy import copy

from fastapi_events.dispatcher import dispatch

from src.events import ScheduleChangedEvent, ScheduleDeletedEvent
from src.requests import ScheduleCreateModel
from src.model import Schedule, Bus, Driver

class ScheduleService(object):
    async def create_schedule(self, scheduleCreateModel: ScheduleCreateModel):
        bus = await Bus.objects.get(id=scheduleCreateModel.bus_id)
        driver = await Driver.objects.get(id=scheduleCreateModel.driver_id)

        schedule = Schedule(bus=bus, driver=driver, begin=scheduleCreateModel.begin, end=scheduleCreateModel.end)
        await schedule.save()
        return schedule

    async def edit_schedule(self, schedule_id: int, scheduleInput: Schedule):
        schedule = await Schedule.objects.select_related(Schedule.bus).select_related(Schedule.driver).get(id=schedule_id)
        if schedule is None:
            return None

        original_schedule = copy(schedule)

        if scheduleInput.driver and scheduleInput.driver.id:
            driver = await Driver.objects.get(id=scheduleInput.driver.id)
            schedule.driver = driver

        if scheduleInput.bus and scheduleInput.bus.id:
            bus = await Bus.objects.get(id=scheduleInput.bus.id)
            schedule.bus = bus

        schedule.begin = scheduleInput.begin
        schedule.end = scheduleInput.end

        await schedule.update()
        dispatch(ScheduleChangedEvent, {"old": original_schedule.__dict__, "new": schedule.__dict__})
        return schedule

    async def delete_schedule(self, schedule_id: int):
        schedule = await Schedule.objects.select_related(Schedule.driver).get_or_none(id=schedule_id)
        if schedule is None:
            return None

        await schedule.delete()
        dispatch(ScheduleDeletedEvent, {"schedule": schedule.__dict__})
        return schedule