'use strict';
const model = require('../models/cargo');
const httpCodes = require('http-status-codes');

module.exports = {
  getAll: (req, res) => {
    return model.find()
      .then(cargo_list => {
        res.json(cargo_list);
      })
      .catch(() => {
        res.status(httpCodes.INTERNAL_SERVER_ERROR).send({ message: 'Internal error' });
      });
  },

  create: (req, res) => {
    return model.create({
      text : req.body.text,
      loaded : req.body.loaded || false
    })
      .then(() => model.find())
      .then(cargo_list => {
        res.status(httpCodes.CREATED).json(cargo_list);
      })
      .catch(() => {
        res.status(httpCodes.INTERNAL_SERVER_ERROR).send({ message: 'Internal error' });
      });
  },

  delete: (req, res) => {
    return model.remove({ _id : req.swagger.params.hashId.value })
      .then(() => model.find())
      .then(cargo_list => {
        return res.json(cargo_list);
      })
      .catch(() => {
        res.status(httpCodes.INTERNAL_SERVER_ERROR).send({ message: 'Internal error' });
      });
  },
};