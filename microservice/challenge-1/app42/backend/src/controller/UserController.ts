import {Request, Response} from "express";

import UserService from "../service/UserService";

class UserController {
    public static getHandler(request: Request, response: Response): void {
        let message = "output is ";
        message += UserService.getAll();
        response.json(message);
    }
}

export default UserController;
