import {Request, Response} from "express";
import User from "../entity/User";

import UserService from "../service/UserService";

class UserController {
    public static getHandler(request: Request, response: Response): void {
        response.json(UserService.getAll().map((user: User) => user.toJson()));
    }
}

export default UserController;
