# Start from the latest golang base image
FROM golang:latest

RUN apt-get update && apt-get install -y wget

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

# Set the Current Working Directory inside the container
WORKDIR /app

RUN go get -d -v github.com/gorilla/mux
RUN go get github.com/jinzhu/gorm
RUN go get github.com/go-sql-driver/mysql

# Copy everything from the current directory to the Working Directory inside the container
COPY . .

# Build the Go app
RUN go build -o booster .

# Command to run the executable
CMD ["./booster"]