FROM node:14-alpine3.14 AS front-end-build

WORKDIR /app

# build front-end
COPY ./ui-domy-neon-v2/package.json ./
COPY ./ui-domy-neon-v2/package-lock.json ./
COPY ./ui-domy-neon-v2 ./

RUN npm i
RUN npm run build

FROM node:14-alpine3.14 AS backend-runtime
WORKDIR /app

COPY ./api-domy-neon-v2/package.json ./
COPY ./api-domy-neon-v2/package-lock.json ./
COPY ./api-domy-neon-v2 ./

RUN npm i
RUN npm run build

COPY --from=front-end-build /app/build /app/dist/client/build

EXPOSE 5000

CMD ["npm", "run", "serve"]
