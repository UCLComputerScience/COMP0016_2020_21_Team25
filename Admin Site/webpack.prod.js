const { merge } = require("webpack-merge");
const common = require("./webpack.common.js");
const CssMinimizerPlugin = require("css-minimizer-webpack-plugin");
const TerserPlugin = require("terser-webpack-plugin");

module.exports = merge(common, {
    mode: "production",
    optimization: {
        mangleWasmImports: true,
        splitChunks: {
            chunks: "all",
        },
        minimize: true,
        minimizer: [
            new CssMinimizerPlugin({
                test: /\.css$/i,
            }),
            new TerserPlugin(),
        ],
    },
});
