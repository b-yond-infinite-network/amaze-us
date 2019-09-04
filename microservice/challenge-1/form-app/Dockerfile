# base image
FROM node:latest as node

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package*.json /app/

RUN npm install
RUN npm install -g @angular/cli@8.3.2

COPY ./ /app
RUN npm run build

FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*
COPY ./dev/nginx.conf /etc/nginx/nginx.conf

COPY --from=node  /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]