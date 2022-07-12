### Docs
Use this commands to start back-end and front-end projects
- cd back-end
- docker build -t fastapi_project .
- docker-compose -f docker-compose.yml up
- docker exec -it fastapi_project_app alembic upgrade head
- cd ../
- cd front-end
- npm run dev

Open link "http://localhost:3000/"

### API documentation
Open link "http://localhost:8000/docs#"

### Next steps
- Extend logic to get schedules. Add query parameters with bus_id or driver_id and date range
- Add authentication. Use OAuth2 with Password (and hashing), Bearer with JWT tokens.
- Add tests. Use Pytest.
