from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.requests import Request
from fastapi.responses import JSONResponse

from src.config import settings
from src.database.db import StartDB
from src.exceptions.app_exception import AppException
from src.exceptions.auth_exception import AuthException
from src.exceptions.db_exception import DBException
from src.routes.api_routes import router as api_router

app = FastAPI(
    docs_url="/",
    version="0.0.1",
    contact={
        "name": "Antonio Junior Souza Silva",
        "email": "antonio.jr.ssouza@gmail.cm",
    },
)

if settings.server and settings.server.get("cors_origins", None):
    app.add_middleware(
        CORSMiddleware,
        allow_origins=settings.server.cors_origins,
        allow_credentials=settings.get("server.cors_allow_credentials", True),
        allow_methods=settings.get("server.cors_allow_methods", ["*"]),
        allow_headers=settings.get("server.cors_allow_headers", ["*"]),
    )


@app.middleware("http")
async def errors_handling(request: Request, call_next):
    try:
        return await call_next(request)
    except (DBException, AppException, AuthException) as exception:
        return JSONResponse(
            status_code=exception.status_code, content=dict(exception)
        )


app.include_router(api_router)


@app.on_event("startup")
def on_startup():
    StartDB(seed=settings.server.seed_db)  # pragma: no cover
