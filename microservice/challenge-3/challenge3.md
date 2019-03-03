# Challenge 3

> The orchestrator used in this project is `docker-compose` in first step.

## Issues (TODO order by priority)

- [ ] Discuss about monorepo or multirepo. Split the projects in four repositories booster, cargo, stage2 and the orchestrator. 
- [ ] Add `.gitignore` in each project to avoid push unnecessary files.
- [ ] Add `.editorconfig` in each project to avoid hard merge.
- [ ] Add the **right** `tool` in each projet.
- [ ] Add `Dockerfile` to create a image.
    - [ ] Add labels http://label-schema.org/rc1/
- [ ] Add a `docker-compose.yml` in each project and in the global project.
- [ ] Add `a automatic build (travis, jenkins, ...)` in each project.
- [ ] Add `a quality tool (coveralls)` in each project.
- [ ] Refactoring to help to test.
- [ ] Add `unit test`.
- [ ] Add `integration test`. 
- [ ] Add `documentation` in each project and the orchestractor.
    - [ ] Documentation on code.
    - [ ] How do we build the application?
    - [ ] How do we use the application (https://www.openapis.org/)?