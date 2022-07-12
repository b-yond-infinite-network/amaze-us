var createError = require('http-errors');
var express = require('express');

var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var schedulesRouter = require('./routes/schedules');
var driversRouter = require('./routes/drivers');
var busesRouter = require('./routes/buses');
var authRouter = require('./routes/auth');
var app = express();
var cors = require ('cors');
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});


var swaggerUi = require('swagger-ui-express')
var swaggerFile = require('./swagger_output.json')
require("dotenv").config();



const PORT = process.env.NODE_DOCKER_PORT || 8080;
console.log('port',PORT)


app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use(cors());


app.use('/', indexRouter);
app.use('/api/v1', authRouter);
app.use('/api/v1', usersRouter);
app.use('/api/v1', schedulesRouter);
app.use('/api/v1', driversRouter);
app.use('/api/v1', busesRouter);
app.use('/doc', swaggerUi.serve, swaggerUi.setup(swaggerFile))


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler


app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

if( process.env.NODE_ENV !='test'){
  app.listen(PORT, () => {
    console.log(`App running on port http://localhost:${PORT}`);
    console.log(
      `OpenAPI documentation available in http://localhost:${PORT}/doc`
    );
  });
}

module.exports = app;
