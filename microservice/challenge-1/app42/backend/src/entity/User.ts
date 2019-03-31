import {Column, Model, Table} from "sequelize-typescript";

@Table
export default class User extends Model<User> {
    @Column
    private _description: string;

    @Column
    private _email: string;

    @Column
    private _name: string;
    private _id: number;

    constructor(name: string, email: string, desc: string, id?: number) {
        super();
        this._description = desc;
        this._email = email;
        this._id = id ? id : -1;
        this._name = name;
    }

    // @Column
    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    // @Column
    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    // @Column
    get email(): string {
        return this._email;
    }

    set email(value: string) {
        this._email = value;
    }

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }
}
