from fastapi import APIRouter

from src.routes.v1_routes.v1_api_routes import router as v1_api_routes

router = APIRouter(prefix="/api")

router.include_router(v1_api_routes)
