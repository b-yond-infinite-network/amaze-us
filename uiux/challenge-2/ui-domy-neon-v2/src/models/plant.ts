export interface Plant {
  seedId: string;
  seedName: string;
  seedUnits: number;
  totalYieldInKg: number;
  notes: string;
  ripeAt: number;
  harvestedAt?: number;
}
