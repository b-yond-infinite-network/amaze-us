var webpackConfig = require("./webpack.config.test.js");
module.exports = {
    "basePath": "",
    "frameworks": ["jasmine"],
    "files": [
        {
            "pattern": "./src/test.ts",
            "watched": false
        }
    ],
    "preprocessors": {
        "./src/test.ts": [
            "webpack",
            "sourcemap"
        ]
    },
    "webpack": webpackConfig,
    "webpackMiddleware": {
        "stats": "errors-only"
    },
    "webpackServer": {
        "noInfo": true
    },
    "reporters": ["progress"],
    "port": 9876,
    "colors": true,
    "browsers": [
        "PhantomJS"
    ]
};
