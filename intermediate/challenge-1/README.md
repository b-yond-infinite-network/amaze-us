# Bus Drivers Shifts 
In this challenge, you are asked to design and implement a REST API that will be responsible of managing the schedules of buses in your city.  
- A bus has a capacity, a model, make, and an associated driver.
- A driver has a first name, last name, social security number
- A schedule links the different buses with drivers on a given day/time  

Here's a list of operations that are required by this API:

- The API should allow a user to manage the schedule and add new entries for buses, drivers and assign shifts to drivers.  
    >For example, Driver John, will be driving Bus XYZ on Wednesday between 8:00 am and 2:00 pm.
- The API should allow a user to retrieve the schedule for every driver for a given week.
- The API should allow a user to retrieve the schedule for every bus for a given week.

## Bonus points
The below items are not required for this challenge, if you wish to wow us, go ahead
- Secure the API
- Add Role Based Access Control, where a user with role `employee` can only read the schedule, whereas a user with role `manager` can create/update/delete buses, drivers and shifts.

## Expected deliverables
- Documentation for your service, pre-req, how to build, how to run
- API documentation
- Code that compiles and runs
- Unit tests
- Optionally docker-compose to run the application

## Expected Stack
Feel free to pick any of the following languages and associated frameworks to solve this challenge:
- Python
- Java
- Scala
- JavaScript
