@echo off

CALL docker run -d --name my-postgresdb-container -p 5442:5432 my-postgres-db