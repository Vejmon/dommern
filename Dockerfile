FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get install -y --no-install-recommends \
        curl ca-certificates libusb-1.0-0 libstdc++6 && \
    curl -fsSL https://github.com/raspberrypi/pico-sdk-tools/releases/download/v2.3.1-0/picotool-2.3.1-x86_64-lin.tar.gz \
      | tar -xz -C /opt && \
    rm -rf /var/lib/apt/lists/* && \
    ln -s /opt/picotool/picotool /usr/local/bin/picotool

RUN picotool help

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]