FROM python:3.8.7

# Setting working directory. All the path will be relative to WORKDIR
WORKDIR /usr/src/app

# Copy files
COPY .. .

# Installing dependencies
RUN pip install -r requirements.txt
ENV PYTHONPATH=/usr/src/app

EXPOSE 8080