const express = require('express');
const rendertron = require('rendertron-middleware');

const app = express();

const renderCallback = rendertron.makeMiddleware({
    proxyUrl: process.env.RENDERTRON_URL
});
const changeHost = function (req, res, next) {
    console.log('[NEW] from ' + req.protocol + '://' + req.get('host') + req.originalUrl);
    return renderCallback.call(this, req, res, next);
};

app.use(function (req, res, next) {
    console.log('[ORIGINAL] from ' + req.protocol + '://' + req.get('host') + req.originalUrl);
    req.headers['host'] = process.env.FULL_APP_HOST;
    return changeHost.call(this, req, res, next);
});

app.use(express.static('files'));
app.get('/*', function (req, res) {
    res.sendFile(__dirname + '/files/index.html');
});
app.listen(3000);