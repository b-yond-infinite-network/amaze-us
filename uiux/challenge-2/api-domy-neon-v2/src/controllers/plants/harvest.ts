import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import PlantsValidations from '../../validations/plants';
import Logger from '../../providers/logger';
import logger from '../../providers/logger';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    await Promise.all(PlantsValidations.harvest.map(v => v.run(req)));
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ validationErrors: errors.array() });
    }
    
    const {
      plantId,
      storageUnitId,
    } = req.body;

    const [plant, storageUnit] = await Promise.all([
      DbContext.plants.findOne({
        id: plantId
      }),
      DbContext.storageUnits.findOne({
        id: storageUnitId
      })
    ])
    
    if (!plant) {
      return res.status(400).json({ error: 'Invalid plant.' });
    }

    if (!storageUnit) {
      return res.status(400).json({ error: 'Invalid storage unit.' });
    }

    const now = new Date().getTime();

    if (plant.ripeAt > now) {
      return res.status(400).json({ error: 'This plant is not ready for harvest.' });
    }

    if (plant.harvestedAt !== undefined) {
      return res.status(400).json({ error: 'This plant has already been harvested.' });
    }

    plant.harvestedAt = now;
    const foodProduce = await DbContext.foodProduce.findOne({
      seedId: plant.seedId,
    });

    foodProduce.stock.push({
      harvestedOn: now,
      plantId: plant.id,
      quantityKg: plant.totalYieldInKg,
      storageUnitId: storageUnit.id,
      storageUnitName: storageUnit.name,
    });

    await Promise.all([
      DbContext.plants.update(plant),
      DbContext.foodProduce.update(foodProduce),
    ])

    return res.status(204).send();
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Plant creation failure.',
      error
    });
    next(error);
  }
}
