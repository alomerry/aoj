module.exports = {
    pages: {
        // 当使用只有入口的字符串格式时，
        // 模板会被推导为 `public/subpage.html`
        // 并且如果找不到的话，就回退到 `public/index.html`。
        // 输出文件名会被推导为 `subpage.html`。
        // subpage: 'src/subpage/index.js'
        admin: {
            entry: 'src/views/admin/admin.js',
            template: 'src/views/admin/components/admin.html',
            filename: 'admin.html',
            title: 'Admin Page',
            // chunks: ['chunk-vendors', 'chunk-common', 'admin']
        },
        index: {
            // page 的入口
            entry: 'src/views/oj/index.js',
            // 模板来源
            template: 'src/views/oj/components/index.html',
            // 在 dist/index.html 的输出
            filename: 'index.html',
            // 当使用 title 选项时，
            // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
            title: 'Index Page',
            // 在这个页面中包含的块，默认情况下会包含
            // 提取出来的通用 chunk 和 vendor chunk。
            // chunks: ['chunk-vendors', 'chunk-common', 'index']
        },
    },
    devServer: {
        historyApiFallback: {
            rewrites: [
                {from: /^\/index/, to: '/index.html'},
                {from: /^\/admin/, to: '/admin.html'},
            ]
        },
        proxy: {
            '/api': {
                target: 'http://localhost:8088',//设置你调用的接口域名和端口号 别忘了加http
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''//这里理解成用‘/api’代替target里面的地址，后面组件中我们掉接口时直接用api代替 比如我要调用'http://40.00.100.100:3002/user/add'，直接写‘/api/user/add’即可
                }
            }
        },
    },

};