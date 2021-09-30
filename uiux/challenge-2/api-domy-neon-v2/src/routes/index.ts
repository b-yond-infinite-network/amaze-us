import { Application } from 'express';
import passport from 'passport';
import path from 'path';

import ApiRoutes from './api';
import AuthRoutes from './auth';

export default class AppRoutes {
  public static mount(app: Application) {
    app.use('/auth', AuthRoutes);
    app.use('/api', passport.authenticate('jwt', { session: false }), ApiRoutes);

    // web routes for serving SPA application
    app.get('*', (_, res) => {
      res.sendFile(path.resolve(__dirname, '../client/build', 'index.html'));
    });
  }
}