import {Column, Model, Table} from "sequelize-typescript";

@Table
export default class User extends Model<User> {

    public id: number | undefined;

    @Column
    public description: string;

    @Column
    public email: string;

    @Column
    public name: string;

    constructor(name: string, email: string, desc: string, id?: number) {
        super();
        this.description = desc;
        this.email = email;
        this.name = name;
    }

    // // @Column
    // get description(): string {
    //     return this._description;
    // }
    //
    // set description(value: string) {
    //     this._description = value;
    // }
    //
    // // @Column
    // get name(): string {
    //     return this._name;
    // }
    //
    // set name(value: string) {
    //     this._name = value;
    // }
    //
    // // @Column
    // get email(): string {
    //     return this._email;
    // }
    //
    // set email(value: string) {
    //     this._email = value;
    // }
    //
    // get id(): number {
    //     return this._id;
    // }
    //
    // set id(value: number) {
    //     this._id = value;
    // }
}
