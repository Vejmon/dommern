import './assets/main.css'

import {createApp} from 'vue'

export async function prepareApp() {
    if (import.meta.env.DEV) {
        const { worker } = await import('./mocks/browser')
        // start() returns a Promise that resolves when the worker is ready
        await worker.start({
            onUnhandledRequest: 'bypass', // Avoid noise in the console
        })
    }
}

// Boot the app after prepareApp finishes
export async function bootstrap(app){
    await prepareApp()
    createApp(app).mount('#app')
}