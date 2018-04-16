FROM node:${node.version}
RUN mkdir -p /opt/base
WORKDIR /opt/base

# pre install this in order to save some time next time the official package.json is updated
COPY init.package.json /opt/base/package.json
RUN npm install

# install packages from the official package.json
RUN rm /opt/base/package.json
COPY base/package.json /opt/base/
RUN npm install
