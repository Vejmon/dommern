# Pi-config

These systemctl services are used to run the dommern-project in production.
They need to be placed in the `/etc/systemd/user/` directory and enabled with `systemctl --user enable <service-name>`.

## dommern.service
the email credentials have been scrambled here, but they are set in the actual service file.

```
[Unit]
Description=Dommern service as a container

[Service]
Environment=EMAIL_PW="gurba"
Environment=EMAIL_USER="gurba@gmail.com"
Environment=SPRING_PI4J_GPIO_PULL="down"
Envitonment=SPRING_PI4j_GPIO_DEBOUNCE=400
WorkingDirectory=/home/vemu/Dommern/dommern
ExecStartPre=/home/linuxbrew/.linuxbrew/bin/docker compose pull --ignore-pull-failures
ExecStart=/home/linuxbrew/.linuxbrew/bin/docker compose up dommern
ExecStop=doc down


[Install]
WantedBy=default.target
```

## firefix.service
The `firefix.service` is dependent on the `dommern.service` and should wait for dommern to respond before starting, see the `ExecStartPre`.
```
[Unit]
Description=Run Firefox on boot

[Service]
WorkingDirectory=/home/vemu
ExecStartPre=/usr/bin/curl --fail --retry 40 --retry-delay 5 --retry-connrefused http://localhost:8080/actuator/health
ExecStart=/usr/bin/firefox --kiosk


[Install]
WantedBy=default.target
```