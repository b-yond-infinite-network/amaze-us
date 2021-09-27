import { BaseEntity } from './base-entity';

export class User extends BaseEntity {
  public firstName: string;
  public lastName: string;
  public dateOfBirth: number;

  public username: string;
  public password: string;

  constructor(
    firstName: string,
    lastName: string,
    dateOfBirth: number,
    id?: string,
  ) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }
};
