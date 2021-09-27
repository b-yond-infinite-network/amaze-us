import { Application } from 'express';

import ApiRoutes from './api';

export default class AppRoutes {
  public static mount(express: Application) {
    express.use('/api', ApiRoutes);
  }
}