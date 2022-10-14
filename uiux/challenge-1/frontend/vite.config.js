import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  define: {
    __APP_ENV__: { ...process.env },
  },
  server: {
    port: 3000,
  },
  preview: {
    host: true,
    port: process.env.PORT || 3000,
  },
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './setupTests.js',
  },
});
