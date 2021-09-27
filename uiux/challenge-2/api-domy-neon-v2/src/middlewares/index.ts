import { Application } from "express";

import Http from './http';
import Auth from './auth';

export default class AppMiddlewares {
  public static register(express: Application) {
    Http.register(express);
    Auth.register(express);
  }
}
