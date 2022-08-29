import uvicorn
from fastapi import FastAPI
from sqlalchemy import create_engine
from sqlalchemy.sql.ddl import CreateSchema
from starlette.middleware.cors import CORSMiddleware

from app.config import settings
from app.config.tags_metadata import tags_metadata
from app.models.driver import Base
from app.routers import driverRouter

app = FastAPI(
    title=settings.TITLE,
    description=settings.DESCRIPTION,
    version=settings.VERSION,
    docs_url=settings.DOCS_PATH,
    openapi_tags=tags_metadata
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.ALLOW_ORIGINS,
    allow_methods=settings.ALLOW_METHODS,
    allow_headers=settings.ALLOW_HEADERS
)


@app.on_event("startup")
def startup():
    engine = create_engine(settings.CONN, echo=settings.DEBUG)
    result = engine.execute(
        f"SELECT schema_name FROM information_schema.schemata WHERE schema_name = '{settings.SCHEMA}';")
    if result.rowcount == 0:
        engine.execute(CreateSchema(settings.SCHEMA))
    Base.metadata.create_all(bind=engine)


app.include_router(driverRouter.router)

if __name__ == "__main__":
    uvicorn.run(
        app=settings.ENTRYPOINT,
        host=settings.HOST,
        port=settings.PORT,
        reload=settings.RELOAD
    )
