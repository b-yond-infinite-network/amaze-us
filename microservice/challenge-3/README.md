# Challenge 3 - It is rocket science!
We were supposed to be on Mars two years ago. Elon is not happy.  
We crashed it again. We checked everything, and it was not the hardware.  
Something must be wrong with our software

Top-notch coders for sure, but they are overworked guaranteeing the future of human race. 
They are stuck in a vicious circle: they fix a bug here, a new one pops up on another system… they fix it, and the first 
bug reappears.

Since you are not a rocket scientist and you don't really like physics all that much, you promise Elon you would fix the 
software. Your plan is to build a system in which whatever the programmers do, they will be able to iteratively build a 
more stable system at each step. There is currently 3 sub-projects, the booster, the stage 2, and the cargo.  
For this to fly, those parts need to be stable separately, but also together!

Of course, to get those developers started, you need to show them how it's done.

Take the project attached, choose the best strategy to improve its quality and start implementing some examples of how 
those rocket programmers need to write the rest.

## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-3)
+ Push your change inside a Pull Request to our master

# Steps to run the code
Run the following command inside the folder `amaze-us/microservice/challenge-3`:
> docker-compose up --build

Go to the browser and enter the URL http://0.0.0.0:8080 

# TODO
+ Implement wait-for service to check if the dependent containers are up and running. Currently, dependent containers fails and restarts several times
until the dependent containers are up
+ Use kubernetes to write the production application, as it is highly scalable, configurable and has a high availability of third party tools
+ Have not checked if there is any issue persists in the cargo and booster services
+ Unittests, integration tests and automation tests needs to be added
+ Organise the different services in different repo using **git modules** will help us manage the repos efficiently
+ Can have a CI/CD to build and deploy the dockerfiles to an artifactory, then pull it from the artifactory for deployments
+ Implement automation test pipeline to test the features, load and stress tests