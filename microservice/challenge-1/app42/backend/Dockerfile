FROM node:8.11

WORKDIR /backend
COPY package.json package-lock.json tsconfig.json ./
RUN ["npm", "install"]
RUN ["npm", "run", "clean"]
COPY . .
RUN ["npm", "run", "build"]
EXPOSE 3000
CMD [ "npm", "start" ]
