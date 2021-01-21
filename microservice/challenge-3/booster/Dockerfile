FROM golang:1.14

# Set the Current Working Directory inside the container
WORKDIR $GOPATH/src/github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3


# Copy everything from the current directory to the PWD (Present Working Directory) inside the container
COPY . $GOPATH/src/github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3

# Download all the dependencies
RUN go get -d -v ./...

# Install the package
RUN go install

# This container exposes port 8080 to the outside world
EXPOSE 3000

# Run the executable
CMD ["booster"]
