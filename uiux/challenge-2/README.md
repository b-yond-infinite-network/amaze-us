# SpaceStack

![Frontend CI](https://github.com/sylhare/SpaceStack/workflows/Frontend%20CI/badge.svg) ![Backend CI](https://github.com/sylhare/SpaceStack/workflows/Backend%20CI/badge.svg)

## Intro

This is a full stack application prototype to manage a Space Colony.
It is composed of:
  - [Frontend](./frontend): A React single page application
  - [Backend](./backend): A Spring Kotlin Rest application.
  
You can run the project using:

```bash
docker-compose up
``` 

You can have more details on each part of the application by checking the readme in their respective folder.

## Functions

The "baby call flow" is handled meaning you can create, get, approve/deny the baby requests.
So far there are no databases and everything is saved in memory.
