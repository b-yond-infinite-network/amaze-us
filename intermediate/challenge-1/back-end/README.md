docker build -t fastapi_project .

docker-compose -f docker-compose.yml up

docker exec -it fastapi_project_app alembic upgrade head
