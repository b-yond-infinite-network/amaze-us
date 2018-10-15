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
            },
            {
                test: /\.scss$/,
                loaders: [
                    'style-loader', 'css-loader', 'sass-loader'
                ]
            },
            {
                test: /\.css$/,
                loader: 'css-loader'
            },
            {
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                use: [{
                    loader: 'file-loader',
                    options: {
                        name: '[name].[ext]',
                        outputPath: 'fonts/',    // where the fonts will go
                        publicPath: '../'       // override the default path
                    }
                }]
            }, {
                test: /.js$/,
                loader: 'source-map-loader',
                enforce: 'pre',
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
