FROM maven:3.6-jdk-11

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
    && apt-get -y install --no-install-recommends python3 2>&1 \
    && apt-get -y install --no-install-recommends apt-utils dialog 2>&1 \
    && apt-get -y install git procps lsb-release \
    && apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*

# Copy build files into docker container
COPY ./Backend Backend
COPY ./services services

# Build Concierge Backend
RUN cd /Backend && mvn package -DskipTests

# Build Concierge Services API
RUN cd ..
RUN cd /services && mvn package -DskipTests

RUN cd ..

# Give execution permission to Concierge Backend start script
RUN chmod u+x /Backend/backend

# Give execution permission to Concierge Services API start script
RUN chmod u+x /services/services

# Give execution permission for the Concierge start script (starts the backend and the services API)
RUN chmod u+x concierge

ENV DEBIAN_FRONTEND=

# Concierge Backend Port
EXPOSE 8100

# Concierge Services API port
EXPOSE 8080