import { BaseEntity } from "./base-entity";

export class StorageUnit extends BaseEntity {
  name: string;
  location: string;
  capacityCubicMeter: number;

  constructor(
    name: string,
    location: string,
    capacityCubicMeter: number,
    id?: string
  ) {
    super(id);
    this.name = name;
    this.location = location;
    this.capacityCubicMeter = capacityCubicMeter;
  }
}