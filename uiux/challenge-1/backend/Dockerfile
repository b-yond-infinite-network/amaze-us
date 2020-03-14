FROM node:10-slim
WORKDIR /drunk-karaoke-backend
COPY ./backend/ ./
COPY ./shared/ ./
RUN npm install
EXPOSE 5000
CMD [ "npm", "run" , "build"]