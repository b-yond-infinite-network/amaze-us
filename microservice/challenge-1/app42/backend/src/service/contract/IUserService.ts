import User from "../../entity/User";

export default interface IUserService {
    getAll(): User[];

    add(user: User): User;
}
