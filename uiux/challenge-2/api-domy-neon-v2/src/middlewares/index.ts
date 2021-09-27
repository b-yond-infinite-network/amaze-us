import { Application } from "express";

import Http from './http';

export default class AppMiddlewares {
  public static register(express: Application) {
    Http.register(express);
  }
}
