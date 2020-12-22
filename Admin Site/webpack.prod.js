const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const webpack = require('webpack');
const path = require('path');
const HtmlCriticalPlugin = require("html-critical-webpack-plugin");


module.exports = merge(common, {
    mode: 'production',
    plugins: [
        new webpack.DefinePlugin({
            __VUE_PROD_DEVTOOLS__: false,
        }),
        new HtmlCriticalPlugin({
            base: path.join(path.resolve(__dirname), 'dist/'),
            src: 'index.html',
            dest: 'index.html',
            inline: true,
            minify: true,
            extract: true,
            width: 1920,
            height: 1080,
            penthouse: {
                blockJSRequests: false,
            }
        })
    ],
    optimization: {
        mangleWasmImports: true,
        splitChunks: {
            chunks: 'all',
        },
        minimize: true,
        minimizer: [
            new CssMinimizerPlugin({
                    test: /\.css$/i,
                },
            ),
            new TerserPlugin(),
        ],
    },
});