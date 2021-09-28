import { BaseEntity } from "./base-entity";

export class WaterCollector extends BaseEntity {
  name: string;
  status: string;
  capacityInLiters: number;
  currentLevelInLiters: number;

  constructor(
    name: string,
    status: string,
    capacityInLiters: number,
    currentLevelInLiters: number,
    id?: string
  ) {
    super(id);
    this.name = name;
    this.status = status;
    this.capacityInLiters = capacityInLiters;
    this.currentLevelInLiters = currentLevelInLiters;
  }

  public isFull() : boolean {
    return this.currentLevelInLiters == this.capacityInLiters;
  }

  public hasCapacityForAmount(amount: number) : boolean {
    if (amount < 0) {
      throw new Error('Amount must be a positive number.');
    }

    return (this.currentLevelInLiters + amount) <= this.capacityInLiters;
  }
}