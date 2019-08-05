export class User {

  name: string;
  email: string;
  description: string;

  public constructor(init?: Partial<User>) {
     Object.assign(this, init);
  }
}
