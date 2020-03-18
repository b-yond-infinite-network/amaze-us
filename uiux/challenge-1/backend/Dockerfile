FROM node:10-slim

# creating a parent folder
RUN mkdir backend

# Backend source code
COPY ./backend ./backend/backend

# We need the shared folder for API response and query param types
COPY ./shared ./backend/shared

# make backend/backend workdir so we can run npm install
WORKDIR /backend/backend
RUN npm install

# Listens on 5000
EXPOSE 5000
CMD [ "npm", "run" , "build"]
