const serverless = require('serverless-http');
const remidersResource = require('./remindersResource');

exports.handler = serverless(remidersResource());