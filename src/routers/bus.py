from fastapi import APIRouter, Depends, Response

from src.responses import Bus as BusResponse
from src.model import Bus, Schedule
from src.parameters import pagination_parameters, BusScheduleFilterParameters
from src.security import JWTBearer, roles
from src.services import BusService


router = APIRouter()


@router.get("/", response_model=list[BusResponse], dependencies=[Depends(JWTBearer(roles.all))])
async def all(pagination_parameters=Depends(pagination_parameters)):
    page, page_size = pagination_parameters["page"], pagination_parameters["page_size"]
    buses = await Bus.objects.paginate(page=page, page_size=page_size).all()
    return buses


@router.get("/{bus_id}", response_model=BusResponse, dependencies=[Depends(JWTBearer(roles.all))], responses={
    200: {"model": BusResponse },
    404: {"description": "Bus not found" }}
)
async def get(bus_id: int, response: Response):
    bus = await Bus.objects.get_or_none(id=bus_id)
    if bus is None:
        response.status_code = 404
        return {"error": "Bus not found"}
    return bus


ScheduleResponse = Schedule.get_pydantic(exclude={"bus", "driver__schedules"})
@router.get("/{bus_id}/schedule", dependencies=[Depends(JWTBearer(roles.all))], response_model=list[ScheduleResponse])
async def get_schedule(bus_id: int, response: Response, filters: BusScheduleFilterParameters = Depends(BusScheduleFilterParameters), busService=Depends(BusService)):
    bus = await busService.get_with_schedules_by_week(bus_id, filters.weekDate)
    return bus.schedules
