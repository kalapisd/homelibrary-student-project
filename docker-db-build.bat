@echo off

CALL docker build -t my-postgres-db -f onlydb.dockerfile .