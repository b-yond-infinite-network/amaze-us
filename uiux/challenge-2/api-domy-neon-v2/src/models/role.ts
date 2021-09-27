import { BaseEntity } from './base-entity';

export class Role extends BaseEntity {
  public name: string;
  public permissions: string[];
  public description?: string;

  constructor(
    name: string,
    permissions: string[],
    description?: string,
    id?: string,
  ) {
    super(id);
    this.name = name;
    this.permissions = permissions;
    this.description = description;
  }
};
