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

#http://label-schema.org/rc1/
LABEL "maintainer"="stephane.jeandeaux@gmail.com" \
      "org.label-schema.vendor"="sjeandeaux" \
      "org.label-schema.schema-version"="1.0.0-rc.1" \
      "org.label-schema.applications.booster.version"=${VERSION} \
      "org.label-schema.build-date"=${BUILD_DATE}

COPY --from=build /src/booster /booster
EXPOSE 3000
ENTRYPOINT ["./booster"] 
