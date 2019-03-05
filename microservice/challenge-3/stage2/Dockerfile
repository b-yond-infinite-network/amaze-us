FROM python:2 as base

WORKDIR /usr/src/app
COPY requirements.txt ./

FROM base as dependencies

RUN pip install --no-cache-dir -r requirements.txt

#TODO FROM dependencies as test

FROM dependencies as release

ARG VERSION=undefined
ARG BUILD_DATE=undefined

#http://label-schema.org/rc1/
LABEL "maintainer"="stephane.jeandeaux@gmail.com" \
      "org.label-schema.vendor"="sjeandeaux" \
      "org.label-schema.schema-version"="1.0.0-rc.1" \
      "org.label-schema.applications.booster.version"=${VERSION} \
      "org.label-schema.build-date"=${BUILD_DATE}

COPY . .
CMD [ "python", "./stage2.py" ]