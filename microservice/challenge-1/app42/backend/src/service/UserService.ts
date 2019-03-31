import User from "../entity/User";
import UserRepository from "../repository/UserRepository";
import IUserService from "./IUserService";

class UserService implements IUserService {

    private userRepository: UserRepository;

    constructor() {
        this.userRepository = new UserRepository();
    }

    public getAll(): Promise<User[]> {
        return this.userRepository.getAll();
    }

    public add(user: User): Promise<User> {
        return this.userRepository.add(user);
    }
}

export default new UserService();
