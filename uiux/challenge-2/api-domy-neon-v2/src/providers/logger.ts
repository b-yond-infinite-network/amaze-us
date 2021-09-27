import log4js from 'log4js';

const logger = log4js.getLogger('Domy-Neon');
logger.level = process.env.LOG_LEVEL || 'info';

export default logger;