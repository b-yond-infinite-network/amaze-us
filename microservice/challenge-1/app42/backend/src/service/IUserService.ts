import User from "../entity/User";

export default interface IUserService {
    getAll(): Promise<User[]>;

    add(user: User): Promise<User>;
}
