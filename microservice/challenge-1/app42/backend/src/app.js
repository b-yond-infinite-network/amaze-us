'use strict';

const createError = require("http-errors");
const express = require("express");
// const log4js = require("log4js");

// import indexRouter from './routes/jukebox';

// const logger = log4js.getLogger(module.filename);

const app = module.exports = express();

app.use(express.json());
app.use(express.urlencoded({extended: false}));

// app.use('/jukebox', indexRouter);

app.use((request, response, next) => {
  next(createError(404));
});

app.use((error, request, response) => {
  response.locals.message = error.message;
  response.locals.error = request.app.get('env') === 'development' ? error : {};

  response.status(error.status || 500);
  response.json({error: 'error'});
});
