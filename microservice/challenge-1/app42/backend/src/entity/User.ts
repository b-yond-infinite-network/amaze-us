import {Column, Model, Table} from "sequelize-typescript";

@Table
export default class User extends Model<User> {

    @Column
    // @ts-ignore
    public description: string;

    @Column
    // @ts-ignore
    public email: string;

    @Column
    // @ts-ignore
    public name: string;

}
