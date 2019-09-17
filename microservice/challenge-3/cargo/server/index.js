const express = require('express')
const app = express()
const cors = require('cors')
const { cargoRouter } = require('./routes')
const bodyParser = require('body-parser')
const path = require('path')
const logger = require('morgan')
const fs = require('fs')
app.use(
  logger(
    'remote-addr - :remote-user [:date[clf]] \':method :url HTTP/:http-version\' \'status:\':status \'res size:\':res[content-length] \'req size:\':req[Content-Length] - \'res time\':response-time ms',
    { stream: fs.createWriteStream('./logs.log', { flags: 'a' }) }
  )
)
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(cors())
app.use('/api/cargo', cargoRouter)
app.use(function (err, req, res, next) {
  res.status(err.status || 500).json({
    status: 'error',
    code: err.status || 500,
    message: err.message || 'Error ' + (err.status || 500)
  })
})
app.use(express.static(path.join(`${__dirname}/../client/public`)))
app.listen(process.env.PORT || 8080)
