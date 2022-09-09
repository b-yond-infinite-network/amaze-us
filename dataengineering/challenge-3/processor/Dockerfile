# syntax=docker/dockerfile:1
FROM python:3.7-alpine
WORKDIR /code
RUN apk update \
&& apk upgrade \
&& apk add bash \
&& apk add openjdk8-jre
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
COPY . .
CMD ["python3", "app.py"]