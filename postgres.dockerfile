FROM postgres:15.1-alpine

LABEL author="Dorottya Kalapis"
LABEL description="Postgres image for Home Library App"
LABEL version="1.0"

COPY ./src/main/resources/db/migration/*.sql /docker-entrypoint-initdb.d/
