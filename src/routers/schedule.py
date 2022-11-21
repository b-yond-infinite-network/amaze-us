from fastapi import APIRouter, Depends, Response

from src.model import Schedule
from src.requests import ScheduleCreateModel
from src.services import ScheduleService
from src.security import JWTBearer, roles
from src.parameters import pagination_parameters


router = APIRouter()


@router.get("/", response_model=list[Schedule], dependencies=[Depends(JWTBearer(roles.all))])
async def list(pagination_parameters=Depends(pagination_parameters)):
    page, page_size = pagination_parameters["page"], pagination_parameters["page_size"]
    Schedules = await Schedule.objects.paginate(page=page, page_size=page_size).all()
    return Schedules


ScheduleGetResponse = Schedule.get_pydantic(exclude={"bus": {"schedules"}})
@router.get("/{schedule_id}", dependencies=[Depends(JWTBearer(roles.all))], responses={
    200: {"model": ScheduleGetResponse},
    404: {"description": "Schedule not found"}}
)
async def get(schedule_id: int, response: Response):
    schedule = await Schedule.objects.select_related(Schedule.bus).select_related(Schedule.driver).get_or_none(id=schedule_id)
    if schedule is None:
        response.status_code = 404
        return {"error": "Schedule not found"}
    return schedule


ScheduleCreateResponse = Schedule.get_pydantic(exclude={"bus": {"schedules"}})
@router.post("/", response_model=ScheduleCreateResponse, dependencies=[Depends(JWTBearer(roles.manager))])
async def create(schedule: ScheduleCreateModel, scheduleService: ScheduleService = Depends(ScheduleService)):
    return await scheduleService.create_schedule(schedule)


ScheduleEditRequest = Schedule.get_pydantic(
    include={"begin", "end", "bus__id", "driver__id"})
@router.put("/{schedule_id}", dependencies=[Depends(JWTBearer(roles.manager))], responses={
    200: {"description": "Schedule successfully edited", "model": ScheduleCreateResponse},
    404: {"description": "Schedule not found"}
})
async def edit(schedule_id: int, schedule: ScheduleEditRequest, response: Response, scheduleService: ScheduleEditRequest = Depends(ScheduleService)):
    schedule = await scheduleService.edit_schedule(schedule_id, schedule)
    if schedule is None:
        response.status_code = 404
        return { "error": "Schedule not found" }
    return schedule

@router.delete("/{schedule_id}", dependencies=[Depends(JWTBearer(roles.manager))], responses={
    204: {"description": "Schedule successfully deleted"},
    404: {"description": "Schedule not found"}
})
async def delete(schedule_id: int, response: Response, scheduleService: ScheduleService = Depends(ScheduleService)):
    schedule = await scheduleService.delete_schedule(schedule_id)
    if schedule is None:
        response.status_code = 404
        return {"error": "Schedule not found"}
    response.status_code = 204