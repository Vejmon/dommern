# Dommern

"The judge" is a timekeeping application for four individual racers around a circuit.

> We went car racing
> - Michael Masi


## Error tracking
any thrown exception will be sent to the configured email address in production mode

## GPIO pinout

![gpio.png](src/main/resources/public/img/gpio.png)

## Development

### UI
The UI is built with Vite, Vue 3 and TailwindCSS.
To start the development server, run:
```bash
cd ui
npm run dev
```
The UI will be available at `http://localhost:5173/local` as default.
The production build is performed when running the gradle process `processResources`.
the msw (mock service worker) is only enabled when running in development.
