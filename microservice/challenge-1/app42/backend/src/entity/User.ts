import {Column, Model, Table} from "sequelize-typescript";

@Table
export default class User extends Model<User> {

    @Column
    public description: string;

    @Column
    public email: string;

    @Column
    public name: string;

}
