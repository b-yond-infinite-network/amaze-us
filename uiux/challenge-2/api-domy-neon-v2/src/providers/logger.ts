import winston, { transports } from 'winston';

const logger = winston.createLogger({
  level: process.env.LOG_LEVEL || 'info',
  transports: [
    new transports.Console({
      level: 'info',
      format: winston.format.combine(
        winston.format.colorize(),
        winston.format.simple()
      )
    })
  ]
});

export default logger;