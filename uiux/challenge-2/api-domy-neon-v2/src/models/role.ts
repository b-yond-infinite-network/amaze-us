import { BaseEntity } from './base-entity';

export class Role extends BaseEntity {
  public name: string;
  public permissions: string[];

  constructor(
    name: string,
    permissions: string[],
    id?: string,
  ) {
    super(id);
    this.name = name;
    this.permissions = permissions;
  }
};
