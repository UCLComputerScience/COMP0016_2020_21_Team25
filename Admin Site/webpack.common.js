const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const {VueLoaderPlugin} = require('vue-loader');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const RobotsTxtPlugin = require("robotstxt-webpack-plugin");
const CopyWebpackPlugin = require("copy-webpack-plugin");

module.exports = {
    mode: process.env.NODE_ENV || 'development',
    entry: {
        app: './src/index.js'
    },
    output: {
        filename: '[name].bundle.min.js',
        path: path.resolve(__dirname, './dist'),
        publicPath: '/',
        crossOriginLoading: 'anonymous'
    },
    plugins: [
        new HtmlWebpackPlugin({
            inject: true,
            template: path.resolve(__dirname, 'public', 'index.html'),
            filename: "index.html",
            favicon: path.resolve(__dirname, 'public', 'favicon.ico')
        }),
        new CleanWebpackPlugin(),
        new MiniCssExtractPlugin({filename: '[name].min.css'}),
        new VueLoaderPlugin(),
        new RobotsTxtPlugin(),
        // Uncomment once you have added images to the specified folder
        // new CopyWebpackPlugin({
        //     patterns: [
        //         {from: path.resolve(__dirname, './src/assets/images'),
        //             to: path.resolve(__dirname, './dist/assets/images')},
        //     ],
        // }),
    ],
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    img: 'src',
                }
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                }
            },
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    'postcss-loader'
                ],
            },
            {
                test: /\.(png|jpe?g|gif|svg|webp)$/i,
                use: ['file-loader'],
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: [
                    'file-loader',
                ],
            },
        ],
    },
};