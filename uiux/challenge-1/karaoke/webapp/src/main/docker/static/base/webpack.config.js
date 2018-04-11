var webpack = require("webpack");
var webpackMerge = require("webpack-merge");
var commonConfig = require("./webpack.common.js");
module.exports = webpackMerge(commonConfig, {
    "devtool": "none",
    "plugins": [
        new webpack.DefinePlugin({
            PRODUCTION: JSON.stringify(true)
        })
    ]
});
