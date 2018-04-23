FROM node:${node.version}
ENV DOC_BASE=/opt/output
RUN mkdir -p /opt/base
RUN mkdir -p /opt/output
COPY --from=${images.owner}/karaoke-static-install:${project.version} /opt/base/node_modules /opt/base/node_modules
COPY base /opt/base
WORKDIR /opt/base
RUN npm run build
ENTRYPOINT ["npm"]
CMD ["run", "build-dev"]