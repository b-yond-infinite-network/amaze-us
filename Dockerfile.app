FROM python:3.8-slim-buster

ARG WORKDIR=/opt/app
RUN mkdir -p ${WORKDIR}

WORKDIR ${WORKDIR}

# * ISNTALL APP REQUIREMENTS
COPY requirements.txt .
RUN pip3 install -r requirements.txt

# * COPY NECESSARY FILES & FOLDERS
COPY assets ./assets
COPY src ./src
COPY app.py .

ENTRYPOINT [ "python3", "app.py" ]
