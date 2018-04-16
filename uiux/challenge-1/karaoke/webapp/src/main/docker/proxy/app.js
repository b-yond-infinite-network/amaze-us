const express = require('express');

const app = express();
const httpProxy = require('http-proxy');
const apiProxy = httpProxy.createProxyServer();

const port = 3000;

app.use('/api', function(req, res) {
    console.log('[API] redirecting to ' + process.env.API_URL + ' from ' + req.protocol + '://' + req.get('host') + req.originalUrl);
    apiProxy.web(req, res, {target: process.env.API_URL + '/api'});
});
app.use(function(req, res) {
    console.log('[RENDERER] redirecting to ' + process.env.RENDERER_URL + ' from ' + req.protocol + '://' + req.get('host') + req.originalUrl);
    apiProxy.web(req, res, {target: process.env.RENDERER_URL});
});

app.listen(port, function() {
    console.log('Listening on port ' + port)
});