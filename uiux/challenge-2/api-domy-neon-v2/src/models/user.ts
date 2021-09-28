import bcrypt from 'bcrypt';
import { BaseEntity } from './base-entity';

export class User extends BaseEntity {
  public firstName: string;
  public lastName: string;
  public birthDate: Date;
  public occupation: string;

  public username: string;
  public password: string;

  constructor(
    firstName: string,
    lastName: string,
    birthDate: Date,
    occupation: string,
    id?: string,
    username?: string,
    password?: string
  ) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.occupation = occupation;
    this.username = username;
    this.password = password;
  }

  async isValidPassword(password: string) : Promise<boolean> {
    return await bcrypt.compare(password, this.password);
  }
};
