const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {
        target: 'https://ywtb.cuit.edu.cn',
        changeOrigin: true, // 是否改变源地址
        pathRewrite: {
          '^/api': '' // 重写路径
        }
      }
    }
  }
    
})

