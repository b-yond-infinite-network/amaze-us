module.exports = {
    "devtool": "inline-source-map",
    "resolve": {
        "extensions": [".ts", ".js"]
    },
    "module": {
        "rules": [
            {
                "test": /\.ts$/,
                "use": ["ts-loader", "angular2-template-loader?keepUrl=true"]
            },
            {
                "test": /\.pug$/,
                "use": ["pug-html-loader"]
            },
            {
                "test": /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)$/,
                "use": ["file?name=assets/[hash].[name].[ext]"]
            },
            {
                "test": /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)\\?.*$/,
                "use": ["file?name=assets/[hash].[name].[ext]"]
            },
            {
                "test": /\.sass$/,
                "use": ["raw-loader", "postcss-loader", "sass - loader"]
            },
            {
                "test": /\.sass\\?global$/,
                "use": ["style-loader", "css-loader", "postcss-loader", "sass-loader"]
            },
            {
                "test": /\.css$/,
                "use": ["raw-loader"]
            },
            {
                "test": /\.css$/,
                "use": ["style-loader", "css-loader"]
            }
        ]
    }
};
