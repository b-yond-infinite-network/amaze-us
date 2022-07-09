from fastapi import APIRouter

from app.api.api_v1.endpoints import driver, bus
# from app.api.api_v1.endpoints import driver, bus, schedule

api_router = APIRouter()
api_router.include_router(driver.router, prefix="/driver", tags=["driver"])
api_router.include_router(bus.router, prefix="/bus", tags=["bus"])
# api_router.include_router(schedule.router, prefix="/schedule", tags=["schedule"])
