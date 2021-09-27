import bcrypt from 'bcrypt';
import { BaseEntity } from './base-entity';

export class User extends BaseEntity {
  public firstName: string;
  public lastName: string;
  public birthDateTimestamp: number;
  public occupation: string;

  public username: string;
  public password: string;

  constructor(
    firstName: string,
    lastName: string,
    birthDateTimestamp: number,
    occupation: string,
    id?: string,
  ) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDateTimestamp = birthDateTimestamp;
    this.occupation = occupation;
  }

  async isValidPassword(password: string) : Promise<boolean> {
    return await bcrypt.compare(password, this.password);
  }
};
