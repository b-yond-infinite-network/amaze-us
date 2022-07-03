FROM python:3.9

WORKDIR /fastapi_project
COPY requirements.txt /fastapi_project/requirements.txt

RUN apt-get update
RUN pip install --upgrade pip
RUN pip install --no-cache-dir --upgrade -r /fastapi_project/requirements.txt
RUN pip freeze


EXPOSE 8000
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000" , "--reload"]
