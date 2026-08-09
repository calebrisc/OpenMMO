FROM eclipse-temurin:25-jre-noble

RUN groupadd --system --gid 10001 openmmo \
    && useradd --system --uid 10001 --gid openmmo --home-dir /opt/openmmo openmmo

WORKDIR /opt/openmmo
COPY server.login/build/install/server.login/ ./
RUN chown -R openmmo:openmmo /opt/openmmo \
    && chmod +x bin/server.login

USER 10001:10001
EXPOSE 2106
ENTRYPOINT ["bin/server.login"]
