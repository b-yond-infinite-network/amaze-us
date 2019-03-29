"use strict";

const http = require("http");
const log4js = require("log4js");
const app = require("./src/app");

const config = require("./src/config");

const logger = log4js.getLogger(module.filename);
logger.level = 'debug';

app.set("port", config.server.port);
const server = http.createServer(app);
server.listen(config.server.port);
server.on("error", error => {
});

server.on("listening", () => logger.info(`BACKEND is listening on port ${config.server.port}`));

process.on("SIGINT", () => {
  logger.info("Stopping BACKEND.");
  server.close(() => {
    logger.info("BACKEND stopped.");
  });
});
