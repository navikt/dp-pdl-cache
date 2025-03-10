FROM gcr.io/distroless/java21

COPY build/libs/dp-pdl-cache-all.jar /app.jar
CMD ["/app.jar"]
