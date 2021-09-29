import { Request, Response, NextFunction } from 'express';
import { check, validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import Logger from '../../providers/logger';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    await Promise.all([
      check('quantity').isFloat({
        min: 0.1
      })
    ]);
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ validationErrors: errors.array() });
    }

    const {
      waterCollectorId,
    } = req.params;

    const {
      quantity
    } = req.body;

    const waterCollector = await DbContext.waterCollectors.findOne({
      id: waterCollectorId,
    });
    
    if (!waterCollector) {
      return res.status(404);
    }

    if (!waterCollector.hasCapacityForAmount(quantity)) {
      return res.status(400).json({ error: 'No capacity for the requested quantity.' });
    }

    waterCollector.currentLevelInLiters += quantity;
    DbContext.waterCollectors.update(waterCollector);

    return res.status(200);
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Water collector refill failed.',
      error
    });
    next(error);
  }
}
