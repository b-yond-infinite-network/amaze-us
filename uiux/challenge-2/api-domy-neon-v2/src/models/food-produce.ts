import { BaseEntity } from "./base-entity";

interface SeedStock {
  plantId: string;
  storageUnitId: string;
  storageUnitName: string;
  quantityKg: number;
  harvestedOn: number;
};

export class FoodProduce extends BaseEntity {
  public name: string;
  public seedId: string;
  public stock: SeedStock[];

  constructor(
    name: string,
    seedId: string,
    stock?: SeedStock[],
    id?: string
  ) {
    super(id);
    this.name = name;
    this.seedId = seedId;
    this.stock = stock ?? [];
  }
}