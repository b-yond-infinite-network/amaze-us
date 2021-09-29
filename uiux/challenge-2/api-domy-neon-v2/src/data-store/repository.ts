import { Role } from "../models/role";
import { User } from "../models/user";
import { UserRole } from "../models/user-role";
import { BaseEntity } from "../models/base-entity";
import { FoodProduce } from "models/food-produce";
import { PlantSeed } from "models/plant-seed";
import { Plant } from "models/plant";
import { StorageUnit } from "models/storage-unit";
import { WaterCollector } from "models/water-collector";
import { BabyMakingRequest } from "models/baby-making-request";

type QueryOpType = 'eq' | 'gt' | 'gte' | 'lt' | 'lte' | 'in';
type FilterOperator = 'and' | 'or';

export interface QueryFilter<T> {
  field: keyof T;
  type: QueryOpType;
  value: any;
};

export interface DBQuery<T> {
  filters?: QueryFilter<T>[];
  operator?: FilterOperator;
};

export interface IRepo<T extends BaseEntity> {
  getById(id: string) : Promise<T>;
  getAll(limit?: number, offset?: number) : Promise<T[]>
  find(query: DBQuery<T> | Partial<T>) : Promise<T[]>;
  findOne(query: DBQuery<T> | Partial<T>) : Promise<T>;
  
  insert(entity: T) : Promise<void>;
  update(entity: T) : Promise<void>;
  remove(entity: T) : Promise<void>;
};

export interface IDatabaseContext {
  users: IRepo<User>;
  roles: IRepo<Role>;
  userRoles: IRepo<UserRole>;
  foodProduce: IRepo<FoodProduce>;
  plantSeeds: IRepo<PlantSeed>;
  plants: IRepo<Plant>;
  storageUnits: IRepo<StorageUnit>;
  waterCollectors: IRepo<WaterCollector>;
  babyMakingRequest: IRepo<BabyMakingRequest>;
};
