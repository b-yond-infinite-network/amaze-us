import {Router} from "express";

import UserController from "../controller/UserController";

class UserRouter {
    private readonly router: Router;

    constructor() {
        this.router = Router();
    }

    public getRouter(): Router {
        this.router.get("/", UserController.getHandler);
        this.router.post("/", UserController.postHandler);
        return this.router;
    }
}

export default new UserRouter();
