import express from 'express';

import AppMiddlewares from './middlewares';
import AppRoutes from './routes';
import Logger from './providers/logger';
import ConfigManager from './providers/configuration';

const app = express();

ConfigManager.init();
AppMiddlewares.register(app);
AppRoutes.mount(app);

const port = process.env.PORT || 5000;

app.listen(port, () => {
  Logger.info(`Express server running at 0.0.0.0:${port}`);
});