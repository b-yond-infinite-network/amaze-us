from fastapi import APIRouter, Depends, Response

from src.model import Schedule
from src.requests import ScheduleCreateModel
from src.services import ScheduleService
from src.parameters import pagination_parameters


router = APIRouter()


@router.get("/", response_model=list[Schedule])
async def list(pagination_parameters=Depends(pagination_parameters)):
    page, page_size = pagination_parameters["page"], pagination_parameters["page_size"]
    Schedules = await Schedule.objects.paginate(page=page, page_size=page_size).all()
    return Schedules


ScheduleGetResponse = Schedule.get_pydantic(exclude={"bus": {"schedules"}})
@router.get("/{schedule_id}", response_model=ScheduleGetResponse, responses={
    200: {"model": Schedule},
    404: {"description": "Bus not found"}}
)
async def get(schedule_id: int, response: Response):
    schedule = await Schedule.objects.select_related(Schedule.bus).select_related(Schedule.driver).get(id=schedule_id)
    if schedule is None:
        response.status_code = 404
        return {"error": "Schedule not found"}
    return schedule


ScheduleCreateResponse = Schedule.get_pydantic(exclude={"bus": {"schedules"}})
@router.post("/", response_model=ScheduleCreateResponse)
async def create(schedule: ScheduleCreateModel, scheduleService: ScheduleService = Depends(ScheduleService)):
    return await scheduleService.create_schedule(schedule)

ScheduleEditRequest = Schedule.get_pydantic(include={"begin", "end", "bus__id", "driver__id"})
@router.put("/{schedule_id}", response_model=ScheduleCreateResponse)
async def edit(schedule_id: int, schedule: ScheduleEditRequest, scheduleService: ScheduleEditRequest = Depends(ScheduleService)):
    return await scheduleService.edit_schedule(schedule_id, schedule)