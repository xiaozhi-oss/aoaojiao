// https://vitejs.dev/config/
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// 引入 svg 需要的插件
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import { viteMockServe } from 'vite-plugin-mock'
// unocss 插件

export default defineConfig(({ command, mode }) => {
  // 获取各种环境下的对应的变量
  // param1：mode 模式，要加载什么环境的文件
  // param2：对应文件的位置   process.cwd() 获取当前目录位置
  const env = loadEnv(mode, process.cwd())
  return {
    plugins: [
      // unocss 安装
      vue(),
      // mock 服务器配置
      viteMockServe({
        enable: command === 'serve', // 保证开发阶段可以使用 mock 的接口
      }),
      // 创建 SvgIcon 插件，让我们可以使用 svg
      createSvgIconsPlugin({
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]',
      }),
    ],
    resolve: {
      alias: {
        '@': path.resolve(__dirname, './src'), // 相对路径别名配置，使用 @ 代替 src
      },
    },
    // scss 全局变量的配置
    css: {
      preprocessorOptions: {
        scss: {
          javascriptEnabled: true,
          additionalData: '@import "./src/styles/variable.scss";',
        },
      },
    },
    // 代理跨域
    server: {
      proxy: {
        [env.VITE_APP_BASE_API]: {
          target: env.VITE_SERVE,
          // 是否需要代理跨域
          changeOrigin: true,
          // // 路径重写 -> 由于我们项目中的接口是没有 /api 前缀的，所以我们要对路径进行重写
          // rewrite: (path) => path.replace(/^\/api/, ''),
        },
      },
    },
  }
})
