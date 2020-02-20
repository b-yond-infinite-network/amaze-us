FROM node:latest
LABEL maintainer="Mo Rawi <morawi-cg@github.com>"
COPY cargo /cargo
WORKDIR /cargo
RUN npm install mongoose
RUN npm install --save morgan
RUN npm install --save express 
#EXPOSE 8080
CMD [ "node", "server.js" ]

# Build instructions
# docker build -t <your username>/node-web-app .
# docker run -p 8080:8080 -d <your username>/node-web-app

