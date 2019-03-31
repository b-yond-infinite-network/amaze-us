import {Request, Response} from "express";

import User from "../entity/User";
import UserUtil from "../utility/UserUtil";

import UserService from "../service/UserService";

class UserController {
    public static getHandler(request: Request, response: Response): any {
        UserService.getAll().then((users) => {
            console.log(users);
            response.json(users.map((user: User) => UserUtil.toJson(user)));
        });
    }

    public static postHandler(request: Request, response: Response): any {
        const user = UserUtil.toUser(request.body);
        if (UserUtil.isValid(user)) {
            UserService.add(user).then((saveUser: User) => {
                response.json(UserUtil.toJson(saveUser));
            }).catch(() => response.status(400).send({message: "invalid user"}));
        } else {
            response.status(400).send({message: "invalid user"});
        }
    }
}

export default UserController;
