import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools()
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)), // '@' 경로 별칭 설정
    },
  },
  server: {
    host: "0.0.0.0", // 🔹 외부에서 접속 가능하게 설정
    port: 8088, // (기본값 5173, 필요하면 변경 가능)
    proxy: {
      "/api": {
        target: "http://localhost:8081", // Spring Boot 서버 주소
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
})