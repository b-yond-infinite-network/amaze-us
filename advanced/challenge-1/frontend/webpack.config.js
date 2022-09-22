const path = require('path')
const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ReactRefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
require('dotenv').config({ path: '.env' })
const devMode = process.env.NODE_ENV !== 'production'

const plugins = [
  new HtmlWebpackPlugin({
    template: path.join(__dirname, 'public', 'index.html')
  }),
  devMode && new ReactRefreshWebpackPlugin(),
  new MiniCssExtractPlugin()
].filter(Boolean)

module.exports = {
  mode: process.env.NODE_ENV,
  entry: './src/index.tsx',
  devServer: {
    hot: true
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.(tsx|ts)?$/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env', '@babel/preset-react'],
              plugins: [
                devMode && require.resolve('react-refresh/babel')
              ].filter(Boolean)
            }
          },
          {
            loader: 'ts-loader'
          }
        ],
        exclude: /node_modules/
      },
      {
        test: /\.(sa|sc|c)ss$/,
        use: [
          MiniCssExtractPlugin.loader,
          'css-loader',
          'postcss-loader',
          'sass-loader'
        ]
      },
      {
        test: /\.(png|jp(e*)g|svg|gif)$/,
        use: ['file-loader']
      }
    ]
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js']
  },
  devtool: devMode ? 'eval-source-map' : false,
  plugins: plugins
}
