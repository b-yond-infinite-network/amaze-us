import express from 'express';

import Logger from './providers/logger';
import { AppBootstrap } from './providers/bootstrap';

const app = express();

AppBootstrap.init(app);

const port = process.env.PORT || 5000;

app.listen(port, async () => {
  await AppBootstrap.postInit();
  Logger.info(`Express server running at 0.0.0.0:${port}`);
});