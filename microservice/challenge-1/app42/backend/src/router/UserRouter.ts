import {Request, Response, Router} from "express";

class UserRouter {

    private router: Router;

    constructor() {
        this.router = Router();
    }

    public getRouter(): Router {
        this.router.get("/", (request: Request, response: Response) => {
            console.log(request.body);
            response.json("worx");
        });
        return this.router;
    }
}

export default new UserRouter();
