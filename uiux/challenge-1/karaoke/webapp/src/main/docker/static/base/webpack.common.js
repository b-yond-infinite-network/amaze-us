var HtmlWebpackPlugin = require('html-webpack-plugin');
var docBase = process.env.DOC_BASE;
module.exports = {
    mode: 'production',
    optimization: {
        minimize: false
    },
    entry: {
        'polyfills': './src/polyfills.ts',
        'vendor': './src/vendor.ts',
        'app': './src/main.ts'
    },
    output: {
        path: docBase,
        filename: '[name].js',
        publicPath: 'app/',
        chunkFilename: '[id].chunk.js'
    },
    resolve: {
        extensions: ['.js', '.ts']
    },
    module: {
        rules: [
            {
                "test": /\.ts$/,
                "use": [
                    {
                        loader: "ts-loader"

                    },
                    {
                        loader: "angular2-template-loader",
                        options: {
                            keepUrl: true
                        }
                    }
                ]
            },
            {
                "test": /\.pug$/,
                "use": [
                    {
                        loader: "file-loader",
                        options: {
                            name: "[name]-[hash].[ext]"
                        }
                    },
                    {
                        loader: "extract-loader"
                    },
                    {
                        loader: "html-loader",
                        options: {
                            minimize: false
                        }
                    },
                    {
                        loader: "pug-html-loader"

                    }
                ]
            },
            {
                "test": /\.sass$/,
                "use": [
                    {
                        loader: "file-loader",
                        options: {
                            name: "[name]-[hash].[ext]"
                        }
                    },
                    {
                        loader: "extract-loader"
                    },
                    {
                        loader: "css-loader"
                    },
                    {
                        loader: "postcss-loader"
                    },
                    {
                        loader: "sass-loader"
                    }
                ]
            },
            {
                "test": /\.css$/,
                "use": [
                    {
                        loader: "style-loader/url"
                    },
                    {
                        loader: "file-loader",
                        options: {
                            name: "[name]-[hash].[ext]"
                        }
                    },
                    {
                        loader: "extract-loader"
                    },
                    {
                        loader: "css-loader"
                    }
                ]
            },
            {
                "test": /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)$/,
                "use": [
                    {
                        loader: "file-loader",
                        options: {
                            name: "[name]-[hash].[ext]",
                            publicPath: "/app/"
                        }
                    }
                ]
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: '!!raw-loader!pug-html-loader!./src/index.pug',
            favicon: './src/favicon.ico',
            // replace the original index.html by this one manipulated file
            filename: 'index.html',
            chunks: ['polyfills', 'vendor', 'app'],
            hash: true
        })
    ]
};
