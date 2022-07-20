from fastapi import APIRouter

from src.endpoints import (
    auth_endpoint,
    bus_endpoint,
    driver_endpoint,
    schedule_endpoint,
)

router = APIRouter(prefix="/v1")

router.include_router(auth_endpoint.router)
router.include_router(bus_endpoint.router)
router.include_router(driver_endpoint.router)
router.include_router(schedule_endpoint.router)
