FROM node:${node.version}
RUN mkdir -p /opt/base
WORKDIR /opt/base

RUN addgroup --system jdoe
RUN adduser --system --ingroup jdoe jdoe

RUN chown jdoe:jdoe -R /opt

USER jdoe

# test
RUN npm install karma@2.0.0 \
    jasmine-core@3.1.0 \
    karma-jasmine@1.1.1 \
    karma-phantomjs-launcher@1.0.4 \
    karma-sourcemap-loader@0.3.7 \
    karma-webpack@3.0.0 \
    phantomjs-prebuilt@2.1.16 \
    @types/jasmine@2.8.6

# dev
RUN npm install @types/core-js@0.9.46 \
    @types/node@9.6.2 \
    angular2-template-loader@0.6.2 \
    autoprefixer@8.2.0 \
    ts-loader@4.1.0 \
    css-loader@0.28.11 \
    extract-loader@2.0.1 \
    extract-text-webpack-plugin@4.0.0-beta.0 \
    file-loader@1.1.11 \
    html-loader@0.5.5 \
    html-webpack-plugin@3.2.0 \
    node-sass@4.8.3 \
    postcss-loader@2.1.3 \
    precss@3.1.2 \
    pug@2.0.3 \
    pug-html-loader@1.1.5 \
    pug-loader@2.4.0 \
    raw-loader@0.5.1 \
    sass-loader@6.0.7 \
    style-loader@0.20.3 \
    to-string-loader@1.1.5 \
    tslint@5.9.1 \
    tslint-loader@3.6.0 \
    typescript@2.8.1 \
    url-loader@1.0.1 \
    webpack@4.5.0 \
    webpack-cli@2.0.14 \
    webpack-dev-server@3.1.1 \
    webpack-encoding-plugin@0.2.1 \
    webpack-merge@4.1.2

# prod angular
RUN npm install @angular/material@5.2.5 \
    @angular/cdk@5.2.5 \
    @angular/animations@5.2.10 \
    @angular/common@5.2.9 \
    @angular/compiler@5.2.9 \
    @angular/core@5.2.9 \
    @angular/forms@5.2.9 \
    @angular/http@5.2.9 \
    @angular/platform-browser@5.2.9 \
    @angular/platform-browser-dynamic@5.2.9 \
    @angular/router@5.2.9 \
    core-js@2.5.4 \
    rxjs@5.5.8 \
    zone.js@0.8.25

# extra prod
RUN npm install font-awesome@4.7.0
RUN npm install typeface-lato@0.0.54
RUN npm install ngx-infinite-scroll@0.8.4
RUN npm install ngx-moment@2.0.0-rc.0


# install packages from the official package.json
COPY base/package.json /opt/base/
RUN npm install
