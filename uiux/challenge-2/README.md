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

# domyzorgblug

## What is done and features:
- Home welcome page
- Manage Water Levels page
- Animated water containers view
- Default form validations
- Water output load
- Manage Food Levels page
- Vuex store 
- Mocked data emulating some DB response
- Food Farm Output/Input load
- Use of Vue-Router
- Minimal styling

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn run serve
```

### Compiles and minifies for production
```
yarn run build
```

### Run your tests
```
yarn run test
```

### Lints and fixes files
```
yarn run lint
```
