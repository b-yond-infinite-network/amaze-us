export default class User {
    private _description: string;
    private _email: string;
    private _id: number;
    private _name: string;

    constructor(name: string, email: string, desc: string, id?: number) {
        this._description = desc;
        this._email = email;
        this._id = id ? id : -1;
        this._name = name;
    }

    get description(): string {
        return this._description;
    }

    set description(value: string) {
        this._description = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

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
