#It uses the multistaging to create image booster
FROM golang:1.12.0-stretch as build

#go in src, it uses go module to manage dependencies
WORKDIR /src
COPY ./go.mod ./go.sum ./
RUN go mod download

#copy the source
COPY . .

#Build the application
RUN CGO_ENABLED=0 go build -installsuffix 'static' -o ./booster .

FROM scratch
COPY --from=build /src/booster /booster
EXPOSE 3000
ENTRYPOINT ["./booster"] 
