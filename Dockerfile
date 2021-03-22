FROM maven:3.6-jdk-11

ENV DEBIAN_FRONTEND=noninteractive


# Give execution permission for the Concierge start script (starts the backend and the services API)
RUN chmod u+x concierge

ENV DEBIAN_FRONTEND=

# Concierge Backend Port
EXPOSE 8100

# Concierge Services API port
EXPOSE 8080