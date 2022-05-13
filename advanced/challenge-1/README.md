# Bus Drivers Schedule 
In this challenge, you are asked to design and implement an application that will be responsible of managing the schedules of buses in your city.  
- A bus has a capacity, a model, make, and an associated driver.
- A driver has a first name, last name, social security number and an email
- A schedule links the different buses with drivers on a given day/time  

Here's a list of operations that are required by this application:

- The application should allow a user to manage the schedule and add new entries for buses, drivers and assign shifts to drivers.  
    >For example, Driver John, will be driving Bus XYZ on Wednesday between 8:00 am and 2:00 pm.
- The application should allow a user to retrieve the schedule for every driver for a given week.
- The application should allow a user to retrieve the schedule for every bus for a given week.
- The application should allow a user to retrieve top X drivers with more shedules per week, during a number of consecutive weeks (from ... to)

## Data Preparation
This solution should have a storage layer of your choice (DB) and you are asked to provide some way to initialize it with large amount of **random** test data:
- +1000 (1K) unique drivers
- +250 unique buses
- +1000000 (1M) schedules over 3 months period

## FULLSTACK position
> Implement this section only if you are applying for a **FULLSTACK** position.

You will be required to add a single page application that covers:
- User authentication page where a user provides a username and password to be able to login to the application
- The web application's main page will be a simple page with filters and a chart:
  - From & To date filters to select weeks
  - How many drivers per week to include (X)
  - Chart (you select best option) to show top X drivers with more shedules per week
- A navigation option for a second page that will allow the user to view the schedules for every driver for a given week and the schedules for every bus for a given week.

## Bonus points
The below items are not required for this challenge, if you wish to wow us, go ahead
- Secure the API
- Add Role Based Access Control, where a user with role `employee` can only read the schedule, whereas a user with role `manager` can create/update/delete buses, drivers and shifts.
- When a schedule is updated, the driver should be notified by email that a new shift was added or removed.

## Expected deliverables
- Documentation for your service(s), pre-req, how to build, how to run
- API documentation
- Code that compiles and runs
- Unit tests
- Integration or end to end tests
- Optionally docker-compose to run the application

## Expected Stack
Feel free to pick any of the following languages and associated frameworks to solve this challenge:
- Python
- Java
- Scala
- JavaScript
- ReactJs
