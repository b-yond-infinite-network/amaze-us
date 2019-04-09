## Step 1: Build ##

FROM node:10-alpine as builder

COPY package.json package-lock.json ./

## prevent unnecessary npm installs

RUN npm ci && mkdir /karaoke-app && mv ./node_modules ./karaoke-app

WORKDIR /karaoke-app

COPY . .

## Build

RUN npm run build:prod -- --output-path=dist


## Step 2: Setup ##

FROM nginx:1.14.1-alpine

## Copy our default nginx config
COPY nginx/default.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /karaoke-app/dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
