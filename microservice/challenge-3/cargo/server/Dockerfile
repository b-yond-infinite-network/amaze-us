FROM node:11-alpine
RUN mkdir -p /usr/server/app
WORKDIR /usr/server/app
COPY package*.json ./
RUN npm i
COPY . .
EXPOSE  8080
CMD [ "npm", "run", "docker" ]