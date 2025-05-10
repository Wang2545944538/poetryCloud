import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  base: '/user/',
  plugins: [vue()],
  dev: {
    port: '5173', // 指定启动端口
    open: true //启动后是否自动打开浏览器
  },

  resolve: {
    alias: {
      '~': path.resolve(__dirname, './'),
      '@': path.resolve(__dirname, 'src')
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        // 添加全局变量和混合的路径
        additionalData: `
          @import "@/styles/variables.scss";
          @import "@/styles/mixins.scss";
        `
      }
    }
  }
});
