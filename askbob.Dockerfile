FROM python:3.8-slim

ARG DEBIAN_FRONTEND=noninteractive
ARG ASKBOB_SETUP_CONFIG=.

RUN apt update && apt install -y build-essential

COPY ["AskBob Concierge/*", "/askbob/plugins/"]

WORKDIR /askbob

RUN python -m pip install -U pip setuptools wheel

RUN python -m pip install spacy==2.3.5
RUN python -m pip install numpy~=1.17.0
RUN python -m pip install rasa[spacy]==2.2.9
RUN python -m pip install sanic~=20.9.0
RUN python -m pip install coloredlogs~=10.0
RUN python -m pip install --no-deps askbob==0.0.2

RUN python -m spacy download en_core_web_md

RUN python -m askbob --setup $ASKBOB_SETUP_CONFIG

ENTRYPOINT python -m askbob -s

EXPOSE 8000