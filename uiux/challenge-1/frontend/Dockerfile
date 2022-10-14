FROM node:18.10

ARG API_URL
ARG PORT=3000

WORKDIR /usr/src/app/frontend

COPY package*.json ./

RUN npm install

COPY . .

ENV API_URL=$API_URL

RUN npm run build

EXPOSE $PORT

ENV PORT=$PORT

CMD ["npm", "run", "preview", "--host"]
