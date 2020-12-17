# DOMY-SODIUM

# FRONTEND

This front end is built in AngularJS using NGRX, it has 5 modules and the main one (app).
Each module represents one of the tools of the page, the main idea of this is to have the entire tool working alone (maybe just implementing the shared module) but with a really easy  migration flow in case that we want to make that tool to work alone.
In this application you will be able to see in each module (except for Shared) their own Redux architecture (reducer, action and effect).
The main idea is to have any call to the backend be through an effect in order to avoid duplicated code, unexpected calls, and be able to identify quicker where we are asking for backend data.

Translation
We have Spanish and English text, isn’t that great? But unfortunately we don’t have a User panel yet, so if you want to try the translations you can add into your local storage this key:
lang: es
using this (en / es) you will be able to jump from one language to the other. 

Auth
In order to register to the Page an Admin, or person with population access needs to pre-create the account since we are in a Ship and we need to have real data and information about how many people are in this Ship.
At the moment the population module it’s not builded, so to create the user you will need “cURL” or “Postman” or other program that allows you to do CRUD calls.
This is the example how to create the call
	POST - v1/pioneer/add_user_pre_registration
		{
			"first_name": "Manuel",
   			"last_name": "Roca",
   			"birthdate": "{{current_timestamp}}",
   			"recognition_number": "E010001"
}
This is an example, the body must be a JSON and the recognition_number must not be used (it’s Unique).
Also any other content in body (or less) will make the backend to return an error on the params (check Backend documentation for more explanation about this).

Note: There’s an interceptor that any call returning 401 or 403 will automatically remove the old token and ask for the user for a new login session, so if that happens it might mean that you token it’s old (12h live) and you need a new access since the ship’s tools must be protected from people that totally forgot to close their account.

Access
Access it’s not 100% implemented in the FrontEnd, any ways we have in the auth module a reducer that it’s Feature Flag, each feature flag it’s a name of a tool (or access) that the user may have to the site.
For more explanation about how the Feature Flag will work, please check the backend documentation.





What To Do:
The front end it’s the part that more work keeps needing since we have an almost all done backend.
At the moment we have a Register / Register Confirmation / Login / Food Panel / Water Panel tools in our ship management app, but we need to still work on it.
So…


WHAT IS NEXT?
We need to build the population graph, to do this we have an status in our user’s database, with that we can know for true science how many people is in the ship at that moment and how many people will be coming to our ship (since we need to approve their birth and pre create their account).
In the backend documentation we will talk about how to make the graphs and metrics for this, the only thing to know here is that we would receive that information and using chart.js we will display that information so we can make better decisions.
TEST:
	Implement better test, and catch all the edge cases. Critic point.
Food Module:
At the moment our food module it’s basic but it’s builded in a way that we can make it grow, so we have each plantation a total percent that they take in our farm, using that and other information we can do maths to know how many % of the farm it’s equal (for each food, since they have different size, and feeding performance) to % that will use in the storage and % that will feed, or feeding performance. (This also will be explained in the backend documentation, or further steps)
UI/UX debts:
At the moment we need to care more about our pioneers using this app and should be easy and straightforward, almost all of our modules are having 3 states ( main_state, error, pending) using this we should implement more page feedback, for example, spinners and toast with errors, we totally need to handle more the errors and give feedback to the users and make our page easier to navigate.
Also a navbar with back button and the main functionalities so the user can navigate and move in an easier way on our page.
And why not? Work in a nicer UI.
Analytics:
We would like to implement analytics, they are very important so we can know what’s happening on the other side, we will be using Firebase Analytics since this will allow us to implement other needed tools.
Remote Config:
We need to send a quick message, but we can’t make another deploy, that’s a lot of time and we are in the middle of our sprint.
That’s why we are going to implement remote config, anything can happen from one second to the other in our ship, and we want to be able to communicate everything quick.

# BACKEND

Backend is a Express mounted API, i choose to follow this architecture to be able to make every tool grow and be able to split them once they are getting bigger.
The main idea it’s to remove these controllers from this API and leave this API as a gateway and make each tool a microservice, so the entire hard security will be on this express server and using clusters we would be able to communicate with them through this.

Middlewares
This backend has two important middlewares (and one on working).
The first one is the Session that validates that the token that comes in the Header is valid and contains the information that we need, if the token is invalid we will return a 403 and if there’s no token present we will return a 401. (being able to catch them in the front).
The other one is a Param validator, using some interfaces (not exactly interfaces but they work like that, they are JOI objects) we are able to check that the information that comes to the endpoint it’s right and the controller will work properly. If not it will return a 400 error giving the information wich param is failing or it’s not present (or even if we have a extra param)

The third one that’s builded but not currently being used it’s the featureFlag middleware, this is a simple function that will check if that token has the tool that the controller need to give the permission to be used, if not it will return a 403.

Sentry
At this moment this backend is running on a Sentry instance, so any error that we have we can catch very quickly.
Also it’s running the API Performance, so we can check on every endpoint the failure ratio, and the average response time for each.

JsonWebToken
We are using a JWT validation for access, this token is generated via a RSA key that we store in our .ENV, this is since Heroku doesn’t allow to have files in their services, and in this way as well we can change the key very easy in our environments of Heroku panel, or if we are using Terraform we can configure it to use AWS or Google Cloud ENV variables.
The body of this token is the user recognition_number and the features that that user have access to, so in this way we can check very easily on each call if that user has access to that endpoint without doing a new call to our DB and reducing the response time from the backend.

Express Helmet
For security I implemented Express Helmet, it’s a basic standard security but I will give us a bone to start building the API Gateway handling the entire security of our backend.
Also you will see CORS, since this is going to be the gateway in the future we will allow IP to call our backend and once that we start to create microservices we will be able to remove that in every microservices and allow only the cluster IP to do that call.



TypeORM
The ORM that this backend uses it’s TypeORM, any migration that you want to do in the future you will need to do it using this ORM, please be sure to have it in global to generate the migrations, in order to run them i did a script that uses the local (in node_modules) ORM package.

HealthCheckers
Every controller has their own health-checker, so if in the future we start to use some things like Instana to do Synthetic Checks on all the controllers (or even if we split them as microservices) we will be able to continue running those checks.


TO DO:
	Food:
		For food we need to create a table where we will make a relation with the % used in the farm and the % that this food uses in our storage and the % of performance of this food.
	Population
		Create an endpoint to return the actual and predicted information for the chart using the status of the pioneers that are registered.
	Storage:
		Create a Storage table to have description of the food that we have in the storage (as we have in plantations) 
	Crons:
		Create a cron to every day at night all the ready and active status of the farm send them into the storage, so we can have this task automated.
	Test:
		Create all the unit tests for every controller.
	





# OLD #

# Challenge 2 - Journey to Kepler
It is the year 2548. We are now halfway to Kepler 186-f. 
We have spent the last 430 years adapting to our dome, but all is not exactly perfect. 
We have 2000 pioneers traveling with us, and our old 'Domy-Neon' application is crashing daily.

We need to prototype a new app - but because we've not upgraded anything since our departure in 2018, you'll have to use 
430-year-old technologies. I know, how did those guys even live without the ZorgBlugFramework? No idea.

Still, this app needs to be able to tackle the travelers' life:
+ Managing our water levels and the water collector output
+ Managing our food levels and the food production farm output
+ Managing the pioneers population, their current birth authorization and allowing for the projection of those numbers in the future
+ Allowing pioneers to ask for a 'baby making authorization'
+ Allow for the 'Habitat and Survival Management' department to authorize or reject 'baby making authorization' demands

Our travelers are very picky and hate to use old technologies, so we need to make it seamless for them. 
They heard about an old technology called 'responsive' and they find the name cute. 
For sure they want it to work on their old mobile phones, with their old Oss: Android and iOS they were called. 
Of course this app needs to run on all our terminals in the dome. That means Chrome 64 and Firefox 58...

Also, they want to have everything in a single page. 
They hate when the browser spins and spins. 
And lastly, we need to make that app fast and usable. 
These travelers get pickier every generation and we have seven of those still to go…


## Expected steps
+ Create a branch of this project (or fork it in your github account if you prefer)
+ Do you **_thang_** inside this folder (challenge-2)
+ Push your change inside a Pull Request to our master