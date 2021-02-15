const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const PreloadWebpackPlugin = require("preload-webpack-plugin");
const { CleanWebpackPlugin } = require("clean-webpack-plugin");
const { VueLoaderPlugin } = require("vue-loader");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const RobotsTxtPlugin = require("robotstxt-webpack-plugin");

module.exports = {
    mode: process.env.NODE_ENV || "development",
    entry: {
        app: "./src/index.js",
    },
    output: {
        filename: "[name].bundle.min.js",
        path: path.resolve(__dirname, "./dist"),
        publicPath: "/",
        crossOriginLoading: "anonymous",
    },
    plugins: [
        new CleanWebpackPlugin(),
        new VueLoaderPlugin(),
        new MiniCssExtractPlugin({
            filename: "[name].min.css",
            ignoreOrder: true,
        }),
        new HtmlWebpackPlugin({
            inject: true,
            title: "Concierge Portal",
            template: path.resolve(__dirname, "public", "index.html"),
            filename: "index.html",
            favicon: path.resolve(__dirname, "public", "favicon.ico"),
        }),
        new PreloadWebpackPlugin({
            rel: "preload",
            as(entry) {
                if (/\.css$/.test(entry)) return "style";
                if (/\.(woff|woff2|eot|ttf|otf)$/.test(entry)) return "font";
                if (/\.(png|jpe?g|gif|svg|webp)$/i.test(entry)) return "image";
                return "script";
            },
            include: "asyncChunks",
        }),
        new RobotsTxtPlugin(),
    ],
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: "vue-loader",
                options: {
                    img: "src",
                },
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                },
            },
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    "css-loader",
                    "postcss-loader",
                ],
            },
            {
                test: /\.(png|jpe?g|gif|svg|webp)$/i,
                use: ["file-loader"],
            },
            {
                test: /\.(woff|woff2|eot|ttf|otf)$/,
                use: ["file-loader"],
            },
        ],
    },
};
