FROM ${images.owner}/karaoke-tomee:${project.version}
WORKDIR /opt/tomee/webapps
COPY --from=${images.owner}/karaoke-war:${project.version} /opt/ /opt/tomee/webapps/ROOT
COPY --from=${images.owner}/karaoke-static:${project.version} /opt/output /opt/tomee/webapps/ROOT/app
WORKDIR /opt/tomee/