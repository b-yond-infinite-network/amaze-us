from fastapi import FastAPI
import uvicorn
from api.api_v1.api import api_router
from core.config import settings

app = FastAPI(
    title=settings.PROJECT_NAME, openapi_url="/openapi.json"
)


@app.get("/")
async def root():
    return {"message": "Hello World"}

app.include_router(api_router)

if __name__ == '__main__':
    uvicorn.run(app, port=settings.API_PORT, host=settings.API_HOST)

