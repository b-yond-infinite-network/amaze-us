FROM golang:1.12-alpine AS builder

RUN apk add --no-cache git

WORKDIR /tmp/app

# Populate the module cache to improve the build time
COPY ./booster/go.mod .
COPY ./booster/go.sum .
RUN go mod download

# Copy everything from the current directory to the PWD
COPY ./booster .

# Build the app
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -a -installsuffix cgo -o /go/bin/booster

# Start fresh from a smaller image
FROM alpine:3.9

RUN apk add ca-certificates

# Copy the booster executable
COPY --from=builder /go/bin/booster /go/bin/booster

# This container exposes port 8080 to the outside world
EXPOSE 8080

# Run the binary program
CMD ["/go/bin/booster"]


