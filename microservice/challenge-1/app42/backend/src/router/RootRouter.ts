import {Router} from "express";
import RootController from "../controller/RootController";

class RootRouter {
    private readonly router: Router;

    constructor() {
        this.router = Router();
    }

    public getRouter(): Router {
        this.router.get(["/", "/heartbeat"], RootController.getHandler);
        return this.router;
    }
}

export default new RootRouter();
