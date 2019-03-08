const webpack = require('webpack');
module.exports = {
    // 基本路径
    baseUrl: './',

    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $:"jquery",
                jQuery:"jquery",
                "windows.jQuery":"jquery"
            })
        ]
    },

    lintOnSave: undefined
};
