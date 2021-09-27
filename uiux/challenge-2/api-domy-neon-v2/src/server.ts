import express from 'express';
import log4js from 'log4js';

const app = express();
const logger = log4js.getLogger('Domy-Neon');
logger.level = process.env.LOG_LEVEL || 'info';
const port = process.env.PORT || 5000;

app.listen(port, () => {
  logger.info(`Express server running at 0.0.0.0:${port}`);
});

app.use((req, res, __) => {
  logger.info('Hello!');
});