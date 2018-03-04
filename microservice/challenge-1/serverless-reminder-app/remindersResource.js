const serverless = require('serverless-http');
const bodyParser = require('body-parser');
const express = require('express')
const mysql = require('mysql');
const app = express()
const AWS = require('aws-sdk');
const ReminderDAO = require('./RemindersDAO');
//const reminders = ReminderDAO.create();

function errorHandler(error, res){
  console.log(error);
  res.status(500).send('Internal server error');
}

module.exports = function(options){
  options = Object.assign({mysql}, options);
  reminders = ReminderDAO.create({mysql: options.mysql});

  app.use(bodyParser.json({ strict: false }));

  app.get('/', function (req, res) {
    res.send('Hello World!');
  })

  app.get('/reminders', function (req, res) {
    if (req.query.op){
      // ?op parameter present
      const op = req.query.op;
      switch (op) {
        case 'count':
          reminders.count()
            .then(results => {
              res.json(results);
            })
            .catch(errorHandler);
          
          break;
      
        default:
          break;
      }

      return;
    }
    
    reminders.findAll(req.query)
      .then((results) => {
        res.json(results);
      })
      .catch(errorHandler)
  })

  app.get('/reminders/:id', function (req, res) {
    reminders.findById(req.params.id)
    .then((results) => {
      if (results.length > 0){
        res.json(results);
      } else {
        res.status(404).send('Resource not found');
      }
    })
    .catch(errorHandler)
  })

  app.post('/reminders/:id', function (req, res){
    const id = parseInt(req.params.id);
    if (isNaN(id)){
      res.status(400).send('Invalid request: id must be a number');
      return;
    }
    reminders.createReminder(Object.assign({id}, req.body))
      .then(() => {
        res.status(201).send('New resource created')
      })
      .catch(errorHandler);
  })

  app.put('/reminders/:id', function (req, res){
    reminders.updateReminder(req.params.id, req.body)
      .then((results) => {
        if (results.changedRows === 0){
          res.status(404).send('Resource not found');
          return;
        }
        res.send('Resource modified')
      })
      .catch(errorHandler)
  })

  app.delete('/reminders/:id', function (req, res){
    reminders.deleteReminder(req.params.id)
      .then((results) => {
        if (results.changedRows === 0){
          res.status(404).send('Resource not found');
          return;
        }
        res.send('Resource deleted')
      })
      .catch(errorHandler)
  })

  return app;
}