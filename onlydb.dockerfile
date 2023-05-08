FROM postgres:15.1-alpine

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB homelibrary

COPY ./src/main/resources/db/migration/*.sql /docker-entrypoint-initdb.d/