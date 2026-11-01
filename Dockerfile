FROM eclipse-temurin:21

# install picotool in container
RUN apt-get update && apt-get install -y --no-install-recommends curl ca-certificates libusb-1.0-0 && \
    curl -fsSL https://github.com/raspberrypi/pico-sdk-tools/releases/download/v2.3.1-0/picotool-2.3.1-x86_64-lin.tar.gz \
      | tar -xz -C /usr/local/bin && \
    chmod +x /usr/local/bin/picotool && \
    rm -rf /var/lib/apt/lists/*

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]