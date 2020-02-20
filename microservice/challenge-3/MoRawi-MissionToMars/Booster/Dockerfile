From golang:latest

LABEL maintainer="Mo Rawi <morawi-cg@github.com>"


COPY  Booster/booster /booster

WORKDIR /booster

RUN go get github.com/mingrammer/go-todo-rest-api-example

RUN go build -o main .

#EXPOSE 3000

RUN go build -o main .
CMD ["/booster/main"]
# can build separately using this syntax:

# docker build --squash  -t morawi/missiontomarsbooster:latest -f Dockerfile
