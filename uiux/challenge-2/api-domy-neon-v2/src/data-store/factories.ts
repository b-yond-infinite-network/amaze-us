import loki from 'lokijs';
import { Role } from 'models/role';
import { UserRole } from 'models/user-role';
import { User } from 'models/user';
import LokiDBRepo from './lokidb/repository';
import { IDatabaseContext } from './repository';

const db = new loki(process.env.DB_NAME);

export const DbContext: IDatabaseContext = {
  users: new LokiDBRepo(db.addCollection<User>('users')),
  roles: new LokiDBRepo(db.addCollection<Role>('roles')),
  userRoles: new LokiDBRepo(db.addCollection<UserRole>('users')),
};
