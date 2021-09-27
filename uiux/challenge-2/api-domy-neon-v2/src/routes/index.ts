import { Application } from 'express';
import passport from 'passport';

import ApiRoutes from './api';
import AuthRoutes from './auth';

export default class AppRoutes {
  public static mount(express: Application) {
    express.use('/auth', AuthRoutes);
    express.use('/api', passport.authenticate('jwt', { session: false }), ApiRoutes);
  }
}