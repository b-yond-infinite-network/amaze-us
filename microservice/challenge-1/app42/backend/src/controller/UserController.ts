import {Request, Response} from "express";
import User from "../entity/User";

import UserService from "../service/UserService";

class UserController {
    public static getHandler(request: Request, response: Response): any {
        response.json(UserService.getAll().map((user: User) => user.toJson()));
    }

    public static postHandler(request: Request, response: Response): any {
        response.json(UserService.add().toJson());
    }
}

export default UserController;
