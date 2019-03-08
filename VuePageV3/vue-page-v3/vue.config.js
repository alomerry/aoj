const webpack = require('webpack');
module.exports = {
    // 基本路径
    baseUrl: './',

    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                /*SimpleModule:"simple-module",
                // "simple.hotkeys":"simple-hotkeys",
                // simpleHotkeys:"simple-hotkeys",
                // simpleUploader:"simple-uploader",
                // "simple.uploader":"simple-uploader",*/
                $:"jquery",
                jQuery:"jquery",
                "windows.jQuery":"jquery",
            })
        ]
    },

    lintOnSave: undefined
};
