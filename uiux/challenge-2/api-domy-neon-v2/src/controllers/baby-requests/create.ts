import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import BabyRequestValidations from '../../validations/baby-requests';
import Logger from '../../providers/logger';

import { BabyMakingRequest, BabyMakingRequestStatus } from '../../models/baby-making-request';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    await Promise.all(BabyRequestValidations.create.map(v => v.run(req)));
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ validationErrors: errors.array() });
    }

    const {
      plannedForYear,
      notes,
    } = req.body;

    const user = req.user as any;
    const newRequest = new BabyMakingRequest(
      user.sub,
      `${user.firstName} ${user.lastName}`,
      new Date(),
      plannedForYear,
      BabyMakingRequestStatus.Pending,
    );
    newRequest.notes = notes;

    await DbContext.babyMakingRequest.insert(newRequest);

    return res.json({
      message: 'Request created successfully.',
      request: newRequest
    });
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Request creation failure.',
      error
    });
    next(error);
  }
}