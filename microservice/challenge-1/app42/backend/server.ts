import http = require("http");
import log4js = require("log4js");

import appManager = require("./src/AppManager");
import config = require("./src/config");
import db from "./src/database/DbConnection";

const app = appManager.createApp();

const logger = log4js.getLogger(module.filename);
logger.level = "debug";

app.set("port", config.server.port);
const server = http.createServer(app);

db.sync().then(() => {
    logger.info("[i] database connected");
    server.listen(config.server.port);
    server.on("error", (error) => {
        logger.error("[X] " + error);
    });
    server.on("listening", () => logger.info(`BACKEND is listening on port ${config.server.port}`));
}).catch((error) => logger.error("[X] error in database connection", error));

process.on("SIGINT", () => {
    logger.info("Stopping BACKEND.");
    server.close(() => {
        logger.info("BACKEND stopped.");
    });
});
