import { BaseEntity } from "./base-entity";

export class Plant extends BaseEntity {
  public seedId: string;
  public seedName: string;
  public seedUnits: number;
  public totalYieldInKg: number;
  public notes: string;
  public ripeAt: number;
  public harvestedAt?: number;
  
  constructor(
    seedId: string,
    seedName: string,
    seedUnits: number,
    totalYieldInKg: number,
    notes: string,
    ripeAt: number,
    harvestedAt?: number,
    id?: string,
  ) {
    super(id);
    this.seedId = seedId;
    this.seedName = seedName;
    this.seedUnits = seedUnits;
    this.totalYieldInKg = totalYieldInKg;
    this.notes = notes;
    this.ripeAt = ripeAt;
    this.harvestedAt = harvestedAt;
  }
}
