FROM eclipse-temurin:25-jre-noble

RUN groupadd --system --gid 10001 openmmo \
    && useradd --system --uid 10001 --gid openmmo --home-dir /opt/openmmo openmmo

WORKDIR /opt/openmmo
COPY server.game/build/install/server.game/ ./
RUN chown -R openmmo:openmmo /opt/openmmo \
    && chmod +x bin/server.game

USER 10001:10001
EXPOSE 7777
ENTRYPOINT ["bin/server.game"]
