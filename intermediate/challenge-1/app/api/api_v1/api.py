from fastapi import APIRouter

from api.api_v1.endpoints import login, users, driver, bus, schedule

api_router = APIRouter()
api_router.include_router(login.router, prefix="/login", tags=["login"])
api_router.include_router(users.router, prefix="/user", tags=["user"])
api_router.include_router(driver.router, prefix="/driver", tags=["driver"])
api_router.include_router(bus.router, prefix="/bus", tags=["bus"])
api_router.include_router(schedule.router, prefix="/schedule", tags=["schedule"])
