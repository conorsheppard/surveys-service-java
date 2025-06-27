FROM maven:3.9.10-eclipse-temurin-24
WORKDIR /app
COPY . .
RUN export MAVEN_OPTS="--enable-native-access=ALL-UNNAMED --sun-misc-unsafe-memory-access=allow" && mvn test -ntp