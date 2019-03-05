# Challenge 3

[![CircleCI](https://circleci.com/gh/sjeandeaux/amaze-us/tree/feature%2Fstephane-jeandeaux-challenge-3.svg?style=svg)](https://circleci.com/gh/sjeandeaux/amaze-us/tree/feature%2Fstephane-jeandeaux-challenge-3)

> The orchestrator used in this project is `docker-compose` for now.

## Requirements

- [ ] make
- [ ] docker
- [ ] docker-compose

## How to start

```bash
make help
make up-dev
#Edit /etc/hosts and add '127.0.0.1  cargo.localhost booster.localhost'
#TODO find a better way to manage hosts
#open your browser on cargo.localhost or booster.localhost
```


## Architecture

![Architecture](./.misc/architecture.png)

## Issues (TODO order by priority)

- [ ] Discuss about monorepo or multirepo. Split the projects in four repositories booster, cargo, stage2 and the orchestrator. 
- [ ] Add `.gitignore` in each project to avoid push unnecessary files.
- [ ] Add `.editorconfig` in each project to avoid hard merge.
- [ ] Add the **right** `buidl tool`in each projet.
- [ ] Add `Dockerfile` to create an image in each projet.
    - [ ] Add labels http://label-schema.org/rc1/
- [ ] Add a `docker-compose.yml` in each project and in the global project.
- [ ] Add `an automatic build (travis, jenkins, circleci,...)` in each project.
- [ ] Add `a quality tool (coveralls, sonar)` in each project.
- [ ] Refactoring in order to ease testing.
    - [ ] Add `unit test`.
    - [ ] Add `integration test`. 
    - [ ] Add `UI test` (casperjs). 
- [ ] Add `documentation` in each project and the orchestractor.
    - [ ] Documentation on code.
    - [ ] How do we build the application?
    - [ ] How do we use the application (https://www.openapis.org/)?
- [ ] Add metrics for each application
- [ ] Add healthcheck for each application
- [ ] Add logs for each application