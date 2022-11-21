FROM coleifer/sqlite
RUN apk add --no-cache --virtual .build-reqs build-base gcc make \
    && pip install --no-cache-dir cython \
    && pip install --no-cache-dir flask peewee sqlite-web \
    && apk del .build-reqs
EXPOSE 8080
VOLUME /data
WORKDIR /data
CMD sqlite_web -x $SQLITE_DATABASE -H $([ x${CODESPACE_NAME} != x ] && echo ${CODESPACE_NAME}-${PORT}.preview.app.github.dev || echo 0.0.0.0)