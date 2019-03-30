import User from "../entity/User";
import IRepository from "./IRepository";

export default class UserRepository implements IRepository<User> {

    public add(model: User): User {
        return new User("amir", "am@gmail.com", "1st", 1);
    }

    public getAll(): User[] {
        return [
            new User("amir hadi", "ah@gmail.com", "1st", 1),
            new User("amir mohsen", "am@gmail.com", "2nd", 2),
            new User("amir ahmad", "aa@gmail.com", "3rd", 3),
        ];
    }

}
