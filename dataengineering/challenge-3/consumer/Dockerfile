From python:3.10.6-alpine3.16

WORKDIR /app

RUN apk update \
    && apk add openjdk8-jre

COPY . .

RUN pip install -r ./requirements.txt 

CMD ["python3", "app.py"]
