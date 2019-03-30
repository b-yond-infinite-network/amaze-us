import {Request, Response} from "express";

class RootController {
    public static getHandler(request: Request, response: Response): void {
        response.json({ok: true});
    }
}

export default RootController;
