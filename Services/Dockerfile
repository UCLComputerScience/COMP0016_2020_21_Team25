FROM maven:3.6-jdk-11

ENV DEBIAN_FRONTEND=noninteractive


RUN apt-get update \
    && apt-get -y install --no-install-recommends python3 2>&1 \
    && apt-get -y install --no-install-recommends apt-utils dialog 2>&1 \
    && apt-get -y install git procps lsb-release \
    && apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /Services

COPY . .

RUN chmod u+x services
RUN chmod u+x tools/test
RUN chmod u+x tools/coverage
RUN chmod u+x tools/analysis

RUN mvn package -DskipTests

#ENV DEBIAN_FRONTEND=

ENTRYPOINT /Services/services

EXPOSE 8080