import User from "../entity/User";
import IUserService from "./contract/IUserService";

class UserService implements IUserService {

    public getAll(): User[] {
        return [
            new User(1, "amir hadi", "ah@gmail.com", "1st"),
            new User(2, "amir mohsen", "am@gmail.com", "2nd"),
            new User(3, "amir ahmad", "aa@gmail.com", "3rd"),
        ];
    }

    public add(): User {
        return new User(1, "amir", "am@gmail.com", "1st");
    }
}

export default new UserService();
