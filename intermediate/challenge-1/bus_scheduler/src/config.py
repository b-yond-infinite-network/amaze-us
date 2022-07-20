import os

from dynaconf import Dynaconf

HERE = os.path.dirname(os.path.abspath(__file__))

settings = Dynaconf(
    envvar_prefix="bus_schedule",
    preload=[os.path.join(HERE, "default.toml")],
    settings_files=["settings.toml"],
    environments=["development", "production", "testing"],
    env_switcher="ENV",
    load_dotenv=False,
)
