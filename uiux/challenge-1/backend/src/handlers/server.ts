import express, { Express } from "express";
import http, { Server } from "http";

import { applyMiddleware } from "../utils";
import middleware from "../middleware";
import errorHandlers from "../middleware/errorHandling";
import apiRoutes from "../api";

export default (): Server => {
  const app: Express = express();
  // Apply middleware here:
  applyMiddleware(middleware, app);

  // Apply routes here:
  app.use("/api/", apiRoutes);

  // Apply error handling errors:
  applyMiddleware(errorHandlers, app);

  const server = http.createServer(app);
  return server;
};
