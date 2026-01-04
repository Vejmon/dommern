import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'

async function prepareApp() {
  if (import.meta.env.DEV) {
    const { worker } = await import('./mocks/browser')
    // start() returns a Promise that resolves when the worker is ready
    await worker.start({
      onUnhandledRequest: 'bypass', // Avoid noise in the console
    })
  }


}
await prepareApp()

const app = createApp(App)
app.mount('#app')