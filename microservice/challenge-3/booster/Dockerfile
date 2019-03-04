#It uses the multistaging to create a booster image.
FROM golang:1.12.0-stretch as build

#get source
WORKDIR /src

#copy the source
COPY . .

RUN make tools 

RUN make test-coverage

#Build the application
RUN make build

FROM scratch

ARG VERSION=undefined
ARG BUILD_DATE=undefined

#http://label-schema.org/rc1/
LABEL "maintainer"="stephane.jeandeaux@gmail.com" \
      "org.label-schema.vendor"="sjeandeaux" \
      "org.label-schema.schema-version"="1.0.0-rc.1" \
      "org.label-schema.applications.booster.version"=${VERSION} \
      "org.label-schema.build-date"=${BUILD_DATE}

COPY --from=build /src/target/booster /booster
EXPOSE 3000
ENTRYPOINT ["/booster"] 
