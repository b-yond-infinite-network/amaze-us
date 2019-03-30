import User from "../entity/User";
import UserRepository from "../repository/UserRepository";
import IUserService from "./IUserService";

class UserService implements IUserService {

    private userRepository: UserRepository;

    constructor() {
        this.userRepository = new UserRepository();
    }

    public getAll(): User[] {
        return this.userRepository.getAll();
    }

    public add(user: User): User {
        return this.userRepository.add(user);
    }
}

export default new UserService();
