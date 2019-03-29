import IUserService from "./contract/IUserService";

class UserService implements IUserService {

    public getAll(): number {
        return 0;
    }
}

export default new UserService();
