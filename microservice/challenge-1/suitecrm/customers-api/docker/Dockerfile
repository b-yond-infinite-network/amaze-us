FROM suitecrm/jre-base


ADD maven/@@project.build.finalName@@.@@project.packaging@@ /usr/share/suitecrm/lib/
RUN chmod 755 /usr/share/suitecrm/lib/@@project.build.finalName@@.@@project.packaging@@

ENV JAVA_PROCESS="java -Dnetworkaddress.cache.ttl=60 \
                       -Djava.security.egd=file:/dev/./urandom \
                       -XX:+UnlockExperimentalVMOptions \
                       -XX:+UseCGroupMemoryLimitForHeap \
                       -XX:MaxRAMFraction=1 ${JAVA_OPTS} \
                       -jar /usr/share/suitecrm/lib/@@project.build.finalName@@.@@project.packaging@@"

EXPOSE 8080
