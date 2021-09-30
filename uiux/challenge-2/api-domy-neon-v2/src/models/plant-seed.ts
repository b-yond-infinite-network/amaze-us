import { BaseEntity } from "./base-entity";

export class PlantSeed extends BaseEntity {
  public name: string;
  public availableUnits: number;
  public yieldKgPerUnit: number;
  public harvestTimeInSeconds: number;

  constructor(
    name: string,
    availableUnits: number,
    yieldKgPerUnit: number,
    harvestTimeInSeconds: number,
    id?: string,
  ) {
    super(id);
    this.name = name;
    this.availableUnits = availableUnits;
    this.yieldKgPerUnit = yieldKgPerUnit;
    this.harvestTimeInSeconds = harvestTimeInSeconds;
  }
}
