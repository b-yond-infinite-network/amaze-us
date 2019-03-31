import User from "../entity/User";
import IRepository from "./IRepository";

export default class UserRepository implements IRepository<User> {

    public add(user: User): Promise<User> {
        return new Promise<User>((resolve, reject) => {
            user.save().then((saveUser) => {
                resolve(saveUser);
            }).catch((error) => reject(error));
        });
    }

    public getAll(): Promise<User[]> {
        // @ts-ignore
        return User.findAll();
    }

}
