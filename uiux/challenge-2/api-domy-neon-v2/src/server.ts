import express from 'express';
import * as dotenv from 'dotenv';

import AppMiddlewares from './middlewares';
import AppRoutes from './routes';
import Logger from './providers/logger';

const app = express();
const port = process.env.PORT || 5000;

dotenv.config();

AppMiddlewares.register(app);
AppRoutes.mount(app);

app.listen(port, () => {
  Logger.info(`Express server running at 0.0.0.0:${port}`);
});