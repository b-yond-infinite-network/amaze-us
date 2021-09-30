import express, { Application } from 'express';
import { json } from 'body-parser';
import path from 'path';

class Http {
  public static register(app: Application) {
    app.use(json());
    app.use(express.static(path.resolve(__dirname, '../client/build')));
  }
}

export default Http;