# get the base node image
FROM node:14 as builder

# set the working dir for container
WORKDIR /app

# copy the json file first
COPY ./package.json /app
COPY package-lock.json /app

# install npm dependencies
RUN npm install

# copy other project files
COPY . .

# build the folder
CMD npm start
# build the folder
# RUN npm run build

