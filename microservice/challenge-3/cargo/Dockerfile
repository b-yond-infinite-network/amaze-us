FROM alpine:3.5 AS base
RUN apk add --no-cache nodejs-current tini
WORKDIR /root/cargo
ENTRYPOINT ["/sbin/tini", "--"]
COPY package.json .
 
FROM base AS dependencies
RUN npm set progress=false && npm config set depth 0
RUN npm install --only=production 
RUN cp -R node_modules prod_node_modules
RUN npm install
 
 #FROM dependencies AS test
 
FROM base AS release

#http://label-schema.org/rc1/
LABEL "maintainer"="stephane.jeandeaux@gmail.com" \
      "org.label-schema.vendor"="sjeandeaux" \
      "org.label-schema.schema-version"="1.0.0-rc.1" \
      "org.label-schema.applications.booster.version"=${VERSION} \
      "org.label-schema.build-date"=${BUILD_DATE}

COPY --from=dependencies /root/cargo/prod_node_modules ./node_modules
COPY . .
EXPOSE 8080
CMD npm run start