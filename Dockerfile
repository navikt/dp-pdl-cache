FROM gcr.io/distroless/java21-debian12:nonroot

COPY build/libs/dp-pdl-cache-all.jar /app/app.jar
