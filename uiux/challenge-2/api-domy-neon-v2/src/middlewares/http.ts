import { Application } from 'express';
import { json } from 'body-parser';

class Http {
  public static register(express: Application) {
    express.use(json());
  }
}

export default Http;