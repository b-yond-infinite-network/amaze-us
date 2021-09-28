import loki from 'lokijs';
import { Role } from 'models/role';
import { UserRole } from 'models/user-role';
import { User } from 'models/user';
import LokiDBRepo from './lokidb/repository';
import { IDatabaseContext } from './repository';
import { FoodProduce } from 'models/food-produce';
import { PlantSeed } from 'models/plant-seed';
import { Plant } from 'models/plant';
import { StorageUnit } from 'models/storage-unit';
import { WaterCollector } from 'models/water-collector';

const db = new loki(process.env.DB_NAME);

export const DbContext: IDatabaseContext = {
  users: new LokiDBRepo(db.addCollection<User>('users')),
  roles: new LokiDBRepo(db.addCollection<Role>('roles')),
  userRoles: new LokiDBRepo(db.addCollection<UserRole>('userRoles')),
  foodProduce: new LokiDBRepo(db.addCollection<FoodProduce>('foodProduce')),
  plantSeeds: new LokiDBRepo(db.addCollection<PlantSeed>('plantSeeds')),
  plants: new LokiDBRepo(db.addCollection<Plant>('plants')),
  storageUnits: new LokiDBRepo(db.addCollection<StorageUnit>('storageUnits')),
  waterCollectors: new LokiDBRepo(db.addCollection<WaterCollector>('waterCollectors')),
};
