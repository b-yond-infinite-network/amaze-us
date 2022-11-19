from fastapi import FastAPI
from fastapi_events.middleware import EventHandlerASGIMiddleware
from fastapi_events.handlers.local import local_handler
from fastapi.responses import RedirectResponse

import src.events
import src.model
from src.routers import bus, driver, schedule

app = FastAPI()
metadata = src.model.metadata.metadata
app.state.database = src.model.metadata.database

app.include_router(
    bus.router,
    prefix="/bus",
    tags=["bus"],
)

app.include_router(
    driver.router,
    prefix="/driver",
    tags=["driver"],
)

app.include_router(
    schedule.router,
    prefix="/schedule",
    tags=["schedule"],
)

app.add_middleware(EventHandlerASGIMiddleware,
                   handlers=[local_handler],
                   middleware_id=id(app))


@app.get("/", include_in_schema=False)
async def docs_redirect():
    return RedirectResponse(url='/docs')

@app.on_event("startup")
async def startup() -> None:
    database_ = app.state.database
    if not database_.is_connected:
        await database_.connect()


@app.on_event("shutdown")
async def shutdown() -> None:
    database_ = app.state.database
    if database_.is_connected:
        await database_.disconnect()

