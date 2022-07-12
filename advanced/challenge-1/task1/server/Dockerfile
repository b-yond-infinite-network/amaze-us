FROM node:14
WORKDIR /server
COPY package.json .
RUN npm install
COPY . .

RUN npm install -g sequelize-cli
RUN git clone https://github.com/vishnubob/wait-for-it.git

EXPOSE		8080
CMD npm start

