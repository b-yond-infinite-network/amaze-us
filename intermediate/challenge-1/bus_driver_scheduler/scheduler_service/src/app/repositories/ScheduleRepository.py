from datetime import datetime, date
from typing import List

from sqlalchemy import func, or_
from sqlalchemy.orm import Session

from app.models.Schedule import ScheduleModel
from app.schemas.Schedule import ScheduleSchema, ScheduleDb


class ScheduleRepository:
    #  Database layer connect all db operation are done here.
    def __init__(self, session: Session):
        self.db = session

    def post(self, payload: ScheduleSchema) -> ScheduleDb:
        """
        Crete a new schedule.
        :param payload: The data to create a new schedule.
        :return: The newly created schedule.
        """
        schedule_model = ScheduleModel(**payload.dict())
        self.db.add(schedule_model)
        self.db.commit()
        self.db.refresh(schedule_model)
        return schedule_model

    def get(self, id: int) -> ScheduleDb:
        """
        Retrieve a single schedule.
        :param id: The id to search the schedule.
        :return: The found schedule.
        """
        return self.db.query(ScheduleModel).filter(ScheduleModel.id == id).first()

    def get_schedule_between(self, start_dt: date, end_dt: date) -> List[ScheduleDb]:
        """
        Retrieve the schedule between a start and an end data.
        :param start_dt: The start date to search the schedule.
        :param end_dt: The end date to search.
        :return: The list of all schedules found
        """
        return self.db.query(ScheduleModel).filter(or_(func.DATE(ScheduleModel.start_dt) >= start_dt,
                                                   func.DATE(ScheduleModel.start_dt) <= end_dt)).all()

    def update(self, schedule_model: ScheduleModel, payload: ScheduleSchema) -> ScheduleDb:
        """
        Update a single schedule.
        :param schedule_model: The schedule to be updated.
        :param payload: The data to update the schedule.
        :return: The updated schedule.
        """
        schedule_model.bus_id = payload.bus_id
        schedule_model.driver_id = payload.driver_id
        schedule_model.start_dt = payload.start_dt
        schedule_model.end_dt = payload.end_dt
        self.db.commit()
        return schedule_model

    def delete(self, schedule_model: ScheduleModel) -> None:
        """
        Delete a single schedule.
        :param schedule_model: The schedule to be deleted.
        :return: None
        """
        self.db.delete(schedule_model)
        self.db.commit()
