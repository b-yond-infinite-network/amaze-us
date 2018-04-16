FROM node:${node.version}
RUN mkdir -p /opt
WORKDIR /opt

RUN curl -s -o chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get update
RUN dpkg -i chrome.deb; apt-get -fy install
RUN rm chrome.deb

RUN addgroup --system jdoe
RUN adduser --system --ingroup jdoe jdoe
RUN chown jdoe:jdoe -R /opt

USER jdoe

COPY init.package.json /opt/package.json
RUN npm install
COPY package.json /opt/package.json

EXPOSE 3000

ENTRYPOINT ["/usr/local/bin/npm"]
CMD ["run", "rendertron"]