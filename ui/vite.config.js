import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from '@tailwindcss/vite'


// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  const isProduction = mode === 'production';

  return {
    plugins: [
      vue(),
      vueDevTools(),
      tailwindcss(),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
    },
    build: {
      rollupOptions: {
        input: isProduction
            ? { main: 'index.html' }
            : { main: 'local.html' }
      }
    },
    server: {
      proxy: {
        '/api': 'http://localhost:8080'
      }
    },
    safelist: [
      { pattern: /bg-(red|green|gray|yellow)-\d{3}/},
      { pattern: /border-(red|green|gray|yellow)-\d{3}/},
      { pattern: /text-(white|black)/},
      { pattern: /(m|p)-\d{3}/},
    ]
  }
});