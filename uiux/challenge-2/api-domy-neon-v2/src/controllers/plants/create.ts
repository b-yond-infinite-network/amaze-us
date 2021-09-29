import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import PlantsValidations from '../../validations/plants';
import Logger from '../../providers/logger';

import { Plant } from '../../models/plant';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    await Promise.all(PlantsValidations.create.map(v => v.run(req)));
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ validationErrors: errors.array() });
    }
    
    const {
      seedId,
      seedUnits,
      notes,
    } = req.body;

    const seed = await DbContext.plantSeeds.findOne({
      id: seedId
    });

    if (!seed) {
      return res.status(400).json({ error: 'Invalid seed.' });
    }

    if (seed.availableUnits < seedUnits) {
      return res.status(400).json({ error: 'Not enough seed units for the request quantity.' });
    }

    const totalYieldInKg = seed.yieldKgPerUnit * seedUnits;
    const ripeAt = new Date().getTime() + seed.harvestTimeInSeconds * 1000;
    const seedPlant = new Plant(
      seed.id,
      seed.name,
      seedUnits,
      totalYieldInKg,
      notes,
      ripeAt,
    );
    seed.availableUnits -= seedUnits;

    await Promise.all([
      DbContext.plants.insert(seedPlant),
      DbContext.plantSeeds.update(seed),
    ])

    return res.json({
      message: 'Plant created successfully.',
      seedPlant
    });
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Plant creation failure.',
      error
    });
    next(error);
  }
}
