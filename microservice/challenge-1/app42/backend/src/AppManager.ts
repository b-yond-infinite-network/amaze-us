import {Application, Request, Response} from "express";
import express = require("express");
import Log4js from "log4js";

import UserRouter from "./router/UserRouter";

const logger = Log4js.getLogger(module.filename);
logger.level = "debug";

class AppManager {

    private static defaultErrorHandler(error: Error, request: Request, response: Response): void {
        logger.error("[x]]");
        logger.error(error.stack);
        response.status(500).send("Something broke!");
    }

    public app: Application;

    public constructor() {
        this.app = express();
        this.initialize();
        this.setRoutes();
        this.setDefaultErrorHandler();
    }

    private initialize(): void {
        this.app.use(express.json());
        this.app.use(express.urlencoded({extended: false}));
    }

    private setRoutes(): void {
        this.app.use("/users", UserRouter.getRouter());
    }

    private setDefaultErrorHandler(): void {
        this.app.use(AppManager.defaultErrorHandler);
    }

}

export const createApp = () => {
    return new AppManager().app;
};
