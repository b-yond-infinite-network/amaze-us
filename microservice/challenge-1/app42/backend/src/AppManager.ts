import BodyParser from "body-parser";
import {Application, Request, Response} from "express";
import express = require("express");
import Log4js from "log4js";

import RootRouter from "./router/RootRouter";
import UserRouter from "./router/UserRouter";

const logger = Log4js.getLogger(module.filename);
logger.level = "debug";

class AppManager {

    private static defaultErrorHandler(request: Request, response: Response): any {
        return response.status(500).send("Something broke!");
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
        this.app.use(BodyParser.urlencoded({ extended: false }));
        this.app.use(BodyParser.json());
    }

    private setRoutes(): void {
        this.app.use("/", RootRouter.getRouter());
        this.app.use("/users", UserRouter.getRouter());
    }

    private setDefaultErrorHandler(): void {
        this.app.use(AppManager.defaultErrorHandler);
    }

}

const app: Application = new AppManager().app;

export const createApp = () => {
    return app;
};
