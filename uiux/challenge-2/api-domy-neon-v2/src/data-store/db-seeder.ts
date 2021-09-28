import logger from "../providers/logger";
import { IDatabaseContext, IRepo } from "./repository";

import { Role } from "../models/role";
import { User } from "../models/user";
import { UserRole } from "../models/user-role";
import { BaseEntity } from "../models/base-entity";
import { FoodProduce } from "../models/food-produce";
import { PlantSeed } from "../models/plant-seed";
import { StorageUnit } from "../models/storage-unit";
import { WaterCollector } from "../models/water-collector";
import { Plant } from "../models/plant";

export default class DataSeeder {
  constructor(private dbContext: IDatabaseContext) { }

  async seed() {
    logger.info('Seeding data');

    await Promise.all([
      this.seedUsers(),
      this.seedRoles(),
      this.seedUserRoles(),
      this.seedFoodProduce(),
      this.seedPlantSeeds(),
      this.seedPlants(),
      this.seedStorageUnits(),
      this.seedWaterCollectors(),
    ]);
  }

  private async seedUsers() {
    await this.seedFromJsonFile('users', this.dbContext.users, (user) => new User(
      user.firstName,
      user.lastName,
      new Date(Date.parse(user.birthDate)),
      user.occupation,
      user.id,
      user.username,
      user.password
    ));
  }

  private async seedRoles() {
    await this.seedFromJsonFile('roles', this.dbContext.roles, (role) => new Role(
      role.name,
      role.permissions,
      role.id
    ));
  }

  private async seedUserRoles() {
    await this.seedFromJsonFile('user-roles', this.dbContext.userRoles, (userRole) => new UserRole(
      userRole.userId,
      userRole.roleId,
    ));
  }

  private async seedFoodProduce() {
    await this.seedFromJsonFile('food-produce', this.dbContext.foodProduce, (foodProduce) => new FoodProduce(
      foodProduce.name,
      foodProduce.seedId,
      foodProduce.stock,
      foodProduce.id
    ));
  }
  
  private async seedPlantSeeds() {
    await this.seedFromJsonFile('plant-seeds', this.dbContext.plantSeeds, (plantSeed) => new PlantSeed(
      plantSeed.name,
      plantSeed.availableUnits,
      plantSeed.yieldKgPerUnit,
      plantSeed.harvestTimeInSeconds,
      plantSeed.id,
    ));
  }

  private async seedPlants() {
    await this.seedFromJsonFile('plants', this.dbContext.plants, (plant) => new Plant(
      plant.seedId,
      plant.seedName,
      plant.seedUnits,
      plant.totalYieldInKg,
      plant.notes,
      plant.ripeAt,
      plant.harvestedAt,
      plant.id
    ));
  }

  private async seedStorageUnits() {
    await this.seedFromJsonFile('storage-units', this.dbContext.storageUnits, (storageUnit) => new StorageUnit(
      storageUnit.name,
      storageUnit.location,
      storageUnit.capacityCubicMeter,
      storageUnit.id
    ));
  }

  private async seedWaterCollectors() {
    await this.seedFromJsonFile('water-collectors', this.dbContext.waterCollectors, (waterCollector) => new WaterCollector(
      waterCollector.name,
      waterCollector.status,
      waterCollector.capacityInLiters,
      waterCollector.currentLevelInLiters,
      waterCollector.id
    ));
  }
  
  private async seedFromJsonFile<TEntity extends BaseEntity>(
    fileName: string,
    repo: IRepo<TEntity>,
    entityFactory: (seedData: any) => TEntity
  ) {
    const { default: seedData } = await import(`../data/seed/${fileName}.json`);
    logger.info(`Seeding ${seedData.length} items from file ${fileName}.json`);

    const ops = seedData.map((seedItem: any) => repo.insert(entityFactory(seedItem)));

    await Promise.all(ops);
  }
}