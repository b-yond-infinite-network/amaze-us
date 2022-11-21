FROM mcr.microsoft.com/devcontainers/python:3.11

RUN apt update
RUN apt -y install sqlite3
RUN sqlite3 db.sqlite ".databases"