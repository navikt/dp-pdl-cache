FROM ghcr.io/navikt/baseimages/temurin:21

COPY build/libs/dp-pdl-cache-all.jar /app/app.jar
