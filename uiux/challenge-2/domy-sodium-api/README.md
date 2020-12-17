# Domy Sodium API

Domy Sodium it's a REST API in charge of handling all the logic of Domy Sodium App
 
## Installation

To run this service you can do it in your local machine using node, or you can use Docker to run it.	
If you are going to use your own local DB please run the script that is in the script folder, that will create a PostgreSQL database and then you will be able to run the migrations (please remember that you will need to have installed in your machine PostgreSQL)

Runing Local

		1)Once that you have your own DB please configure the credentials in the 
			ENV file
		 2) run “npm i” to get all the dependencies.
		 3) run “npx tsc” to build the application
		 4) run “npm run start” to serve the backend in port 3000

Using Docker

		1)Once that you have your own DB please configure the credentials in the 
			ENV file
		 2) run “docker-compose build”.
		 3) run “docker-compose up”.
		 4) The Application is hosted on “localhost:3000”
        

# ENDPOINTS Documentation


### Auth Controller

#### [POST] - /v1/auth/register
It will validate that this recognition number was already registered, if it’s it will use the credentials on the POST method to create the new user and change the state to “ACTIVE”.
it means that there’s already a user registered with this recognition_number but it’s “APPROVED”, that means that it’s able to be registered but not to log in or use the platform.

Params:
```
	{
		“recognition_number”: string
	    “password”: string
     }
```
Response:

```
	"features": Features[]
	"success": true,
	"user": {
		"first_name": string
		"last_name": string
		"birthdate": string
		"recognition_number": string
```

#### [POST] - /v1/auth/login 

When a user has already the status of “ACTIVE” is able to log in, using the recognition_number and the password the user will be able to log in.
The password is hashed using Cryptho with a SHA1 password, so we don’t store never the real and unprotected password.

```
{
	“recognition_number”: string
	“password”: string
}
```
Response:
```
{
	success: true,
	metadata: { token, date },
	features: Features[],
	user: {
	  first_name: string
	  last_name: string
	  birthdate: Date() [string]
	  recognition_number: string
}
```

### Pioneer Controller

#### [POST] /v1/pioneer/user_pre_registration/:recognition_number

Sending the recognition number as URL Param we can check if this user already has a pre registration value.

Response
```
{
		success: true
}
```

#### [POST] /v1/pioneer/add_user_pre_registration

This will create a pre registration user, it means will make an insert in the DB with a user of state “Approved” this user will be able to register after this call.

```
{
		“first_name”: string
		“last_name”: string
		“birthdate”: Date() [string]
		“recognition_number”: string
}
```
Response
```
{
success: true,
message: 'Pioneer information added',
pioneer: {
	“first_name: string
	“last_name”: string
	“birthdate”: Date() [string]
	“recognition_number”: string
     }
}
```

### Farm Controller

#### [GET] /v1/farm_active_plantation
This will return the plantations with Status Active and the Start Date if today or previous dates.
Response
```
{
success: true
plantation: {
    seed_id: string;
    seed_name: string;
    plantation_percent: number;
    status: string [ENUM]
    plantation_start_date: string;
    plantation_ready_date: string;
    updated_at: string;
    }
}
```

#### [GET] /v1/today_harvest
This endpoint returns the entries that plantation_ready_date is equal to today and the status is Active, since we might have lost some plantation even if the collection day were today.
Response:
```
{
success: true
plantation: []Plantation
}
```

#### [POST] /v1/add_plantation
This endpoint will allow the user to add a plantation, always remember that we are using percent in our calculation, so if we have used a 80% of the farm we will be able to add only 20% extra, more than this will return an error of 400 and we won’t be able to create the new plantation.

Request
```
{
amount: number;
readyOnDays: number;
seedId: string;
seedName: string
}
```

Response:
```
{
success: true
plantation: Plantation
}
```

### Collector Controller
#### [GET] v1/collector/water_level
Returns the water collector level
Response:
```
{
success: true
collector: {
    input: number
    output: number
    tool_name: string
    available_capacity: number (represents percent of used)
    updated_at: string
    }
}
```

###	[GET] /v1/collector/food_lavel
Return the food collector level
Response:
```
{
success: true
collector: Collector
}
```

#### [POST]  /v1/collector/add_collectors
At this moment this endpoint will add the basic collectors (Food & Water), it doesn’t require any body since everything is configured in the service.
Response:
```
{
success: true
}
```

## Greeting
Thanks for taking the time to evaluate this challenge, i know it's not 100% full since i tried to make all in one week, any change request or doubt i would be glad to answer.
Again, thanks 
