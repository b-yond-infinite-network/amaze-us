import typer
import uvicorn

from .config import settings
from .database.db import StartDB

cli = typer.Typer(name="bus_schedule API")


@cli.command()
def run(
    port: int = settings.server.port,
    host: str = settings.server.host,
    log_level: str = settings.server.log_level,
    reload: bool = settings.server.reload,
    seed_db: bool = settings.server.seed_db,
):  # pragma: no cover
    """Run the API server."""
    uvicorn.run(
        "src.main:app",
        host=host,
        port=port,
        log_level=log_level,
        reload=reload,
    )


@cli.command()
def seed_db(seed: bool = settings.server.seed_db):
    """Create a database if not exists and seed it"""
    StartDB(seed=seed)  # pragma: no cover
