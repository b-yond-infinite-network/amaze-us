import { BaseEntity } from './base-entity';

export class UserRole extends BaseEntity {
  public userId: string;
  public roleId: string;

  constructor(
    userId: string,
    roleId: string,
    id?: string,
  ) {
    super(id);
    this.userId = userId;
    this.roleId = roleId;
  }
};
