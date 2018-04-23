FROM node:${node.version}
RUN mkdir -p /opt
WORKDIR /opt

COPY init.package.json /opt/package.json
RUN npm install
COPY package.json /opt/package.json
COPY --from=${images.owner}/karaoke-static:${project.version} /opt/output /opt/app
COPY app.js .

EXPOSE 3000

ENTRYPOINT ["npm"]
CMD ["run", "express"]