FROM node:9

ARG NODE_ENV=development 

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY . ./

# set the env vars  and Install app dependencies
RUN NODE_ENV="$NODE_ENV" npm --quiet install

ENTRYPOINT ["npm", "run"]

CMD ["start"]