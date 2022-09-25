From python:3.10.6-alpine3.16

WORKDIR /app

COPY requirements.txt .

RUN apk --no-cache add musl-dev linux-headers g++

RUN pip install --upgrade pip

RUN pip install -r ./requirements.txt 

COPY . .

CMD ["python3", "app.py"]
