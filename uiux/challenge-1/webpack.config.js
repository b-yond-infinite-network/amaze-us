const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');

const config = {
    entry: ['./src/app.tsx'],
    mode: 'development',
    devtool: 'source-map',
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'karaoke.js'
    },
    resolve: {
        extensions: ['.ts', '.tsx', '.js']
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loader: 'awesome-typescript-loader',
                exclude: /node_modules/
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './index.html',
            hash: true,
            title: 'Karaoke',
        })
    ],
    devServer: {
        compress: true,
        inline: true,
        open: true,
        port: 8080,
    }
}

module.exports = config;
