FROM node:11-alpine
RUN mkdir -p /usr/client/app
COPY package*.json ./
RUN npm i
COPY . .
EXPOSE 3000
CMD ["npm", "start"]