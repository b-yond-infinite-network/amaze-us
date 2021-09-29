interface SeedStock {
  plantId: string;
  storageUnitId: string;
  storageUnitName: string;
  quantityKg: number;
  harvestedOn: number;
};

export interface FoodProduce {
  name: string;
  seedId: string;
  stock: SeedStock[];
}