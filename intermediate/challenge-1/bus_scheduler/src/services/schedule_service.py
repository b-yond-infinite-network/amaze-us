from datetime import date
from typing import List

from fastapi import status
from sqlalchemy import and_
from sqlalchemy.orm import Session

from src import services
from src.exceptions.app_exception import AppException
from src.models import Bus, Schedule
from src.schemas.schedule_schema import ScheduleCreate, ScheduleUpdate
from src.services.base_service import BaseService, CreateSchemaType, ModelType


class ScheduleService(BaseService[Schedule, ScheduleCreate, ScheduleUpdate]):
    __db: Session
    __input_data: CreateSchemaType

    def __check_driver_data_consistency(self):
        driver_schedules = self.get_all_driver_day_schedules(
            db_session=self.__db,
            driver_id=self.__input_data.driver_id,
            day=self.__input_data.day,
        )

        if not driver_schedules:
            return

        for driver_schedule in driver_schedules:
            is_inside_previous_schedule_time = (
                driver_schedule.start_hour >= self.__input_data.start_hour
                or driver_schedule.end_hour >= self.__input_data.end_hour
            )
            is_input_bus_equals_to_db_bus = (
                self.__input_data.bus_id == driver_schedule.bus_id
            )

            if is_inside_previous_schedule_time:
                if is_input_bus_equals_to_db_bus is False:
                    message = "Driver already has another schedule with another bus in the same date and time"
                    raise AppException(
                        message=message,
                        error=True,
                        status_code=status.HTTP_409_CONFLICT,
                    )
                if is_input_bus_equals_to_db_bus:
                    message = "Driver already has a schedule with this same bus on this date"
                    raise AppException(
                        message=message,
                        error=True,
                        status_code=status.HTTP_409_CONFLICT,
                    )

    def __check_bus_data_consistency(self) -> None:
        db_bus: Bus = services.bus.get(
            db_session=self.__db, id=self.__input_data.bus_id
        )
        if not db_bus:
            message = "The bus does not exists"
            raise AppException(message=message, error=True)

        if db_bus.driver_id is not None:
            return

        self.__check_driver_data_consistency()
        new_bus = db_bus
        new_bus.driver_id = self.__input_data.driver_id
        services.bus.update(
            db_session=self.__db, db_obj=db_bus, input_object=new_bus.__dict__
        )

    def get_all_driver_day_schedules(
        self, db_session: Session, driver_id: int, day: date
    ):
        return (
            db_session.query(self.model)
            .filter(
                and_(self.model.driver_id == driver_id, self.model.day == day)
            )
            .all()
        )

    def get_all_bus_day_schedules(
        self, db_session: Session, bus_id: int, day: date
    ):
        return (
            db_session.query(self.model)
            .filter(and_(self.model.bus_id == bus_id, self.model.day == day))
            .all()
        )

    def get_all_driver_week_schedules(
        self,
        db_session: Session,
        driver_id: int,
        from_date: date = None,
        to_date: date = None,
    ) -> List[ModelType]:
        return (
            db_session.query(self.model)
            .filter(
                and_(
                    self.model.driver_id == driver_id,
                    self.model.day.between(from_date, to_date),
                )
            )
            .all()
        )

    def get_all_bus_week_schedules(
        self,
        db_session: Session,
        bus_id: int,
        from_date: date = None,
        to_date: date = None,
    ) -> List[ModelType]:
        return (
            db_session.query(self.model)
            .filter(
                and_(
                    self.model.bus_id == bus_id,
                    self.model.day.between(from_date, to_date),
                )
            )
            .all()
        )

    def create(
        self, db_session: Session, *, input_object: CreateSchemaType
    ) -> ModelType:
        self.__db = db_session
        self.__input_data = input_object

        self.__check_bus_data_consistency()
        self.__check_driver_data_consistency()
        return super(ScheduleService, self).create(
            db_session=self.__db, input_object=self.__input_data
        )


schedule = ScheduleService(Schedule)
