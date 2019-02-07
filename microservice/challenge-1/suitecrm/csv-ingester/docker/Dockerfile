FROM suitecrm/jre-base

RUN mkdir /datasets

ADD maven/@@project.build.finalName@@.@@project.packaging@@ /usr/share/suitecrm/lib/
RUN chmod 755 /usr/share/suitecrm/lib/@@project.build.finalName@@.@@project.packaging@@

#this is bad , but just do it for this showcase (to avoid any denied acces to run demo)
RUN chmod 777  -R /datasets

VOLUME /datasets

ENV JAVA_PROCESS="java -Djava.security.egd=file:/dev/./urandom \
                       -XX:+UnlockExperimentalVMOptions \
                       -XX:+UseCGroupMemoryLimitForHeap \
                       -XX:MaxRAMFraction=1 ${JAVA_OPTS} \
                       -jar /usr/share/suitecrm/lib/@@project.build.finalName@@.@@project.packaging@@"