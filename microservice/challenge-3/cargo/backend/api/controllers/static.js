'use strict';
const httpCodes = require('http-status-codes');

module.exports = {
  healthCheck: (req, res) => {
    res.status(httpCodes.OK).send('OK');
  }
};
