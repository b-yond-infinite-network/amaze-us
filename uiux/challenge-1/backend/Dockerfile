FROM node:18.10

WORKDIR /usr/src/app/backend

COPY package*.json ./

RUN npm install --omit=dev

COPY . .

CMD ["npm", "run", "start"]
