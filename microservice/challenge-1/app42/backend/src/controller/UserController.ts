import {Request, Response} from "express";
import HttpErrors from "http-errors";

import User from "../entity/User";
import UserUtil from "../utility/UserUtil";

import UserService from "../service/UserService";

class UserController {
    public static getHandler(request: Request, response: Response): any {
        response.json(UserService.getAll().map((user: User) => UserUtil.toJson(user)));
    }

    public static postHandler(request: Request, response: Response): any {
        const user = UserUtil.toUser(request.body);
        if (UserUtil.isValid(user)) {
            response.json(UserUtil.toJson(UserService.add(user)));
        } else {
            response.json(HttpErrors(400, "error"));
        }
    }
}

export default UserController;
