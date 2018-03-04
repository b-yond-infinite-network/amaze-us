// index.js

const serverless = require('serverless-http');
const bodyParser = require('body-parser');
const express = require('express')
const app = express()
const AWS = require('aws-sdk');
const ReminderDAO = require('./RemindersDAO');
const reminders = ReminderDAO.create();
// const mysql      = require('mysql');
// const pool = mysql.createPool({
//   host     : 'jax-rs-db.cce8xk4qewtv.us-east-1.rds.amazonaws.com',
//   user     : 'root',
//   password : '12345678',
//   database : 'reminders',
//   acquireTimeout: 5000
// });
// const promisify = require('util.promisify');
// const query = promisify(pool.query.bind(pool));


// const USERS_TABLE = process.env.USERS_TABLE;

// const IS_OFFLINE = process.env.IS_OFFLINE;
// let dynamoDb;
// if (IS_OFFLINE === 'true') {
//   dynamoDb = new AWS.DynamoDB.DocumentClient({
//     region: 'localhost',
//     endpoint: 'http://localhost:8000'
//   })
//   console.log(dynamoDb);
// } else {
//   dynamoDb = new AWS.DynamoDB.DocumentClient();
// };


app.use(bodyParser.json({ strict: false }));

app.get('/', function (req, res) {
  res.send('Hello World!');
})

app.get('/reminders', function (req, res) {
  reminders.findAll(req.query)
  .then((results) => {
    res.json(results);
  })
  .catch(error => {
    console.log(error);
    res.status(500);
  })
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
  .catch(error => {
    console.log(error);
    res.status(500);
  })
})

app.get('/test', function(req, res){
  Promise.resolve().then(() => {
    res.send('OK after resolving a promise');
  })
})

// Get User endpoint
// app.get('/users/:userId', function (req, res) {
//   const params = {
//     TableName: USERS_TABLE,
//     Key: {
//       userId: req.params.userId,
//     },
//   }

//   dynamoDb.get(params, (error, result) => {
//     if (error) {
//       console.log(error);
//       res.status(400).json({ error: 'Could not get user' });
//     }
//     if (result.Item) {
//       const {userId, name} = result.Item;
//       res.json({ userId, name });
//     } else {
//       res.status(404).json({ error: "User not found" });
//     }
//   });
// })

// // Create User endpoint
// app.post('/users', function (req, res) {
//   const { userId, name } = req.body;
//   if (typeof userId !== 'string') {
//     res.status(400).json({ error: '"userId" must be a string' });
//   } else if (typeof name !== 'string') {
//     res.status(400).json({ error: '"name" must be a string' });
//   }

//   const params = {
//     TableName: USERS_TABLE,
//     Item: {
//       userId: userId,
//       name: name,
//     },
//   };

//   dynamoDb.put(params, (error) => {
//     if (error) {
//       console.log(error);
//       res.status(400).json({ error: 'Could not create user' });
//     }
//     res.json({ userId, name });
//   });
// })

module.exports.handler = serverless(app);