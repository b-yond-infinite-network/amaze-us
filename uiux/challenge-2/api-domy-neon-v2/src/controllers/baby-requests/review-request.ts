import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import BabyRequestValidations from '../../validations/baby-requests';
import Logger from '../../providers/logger';

export default async (req: Request, res: Response, next: NextFunction) => {
  try {
    await Promise.all(BabyRequestValidations.review.map(v => v.run(req)));
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ validationErrors: errors.array() });
    }

    const {
      status,
      notes,
    } = req.body;
    const { requestId } = req.params;

    const user = req.user as any;
    const request = await DbContext.babyMakingRequest.findOne({
      id: requestId
    });

    if (!request) {
      return res.status(404);
    }

    request.status = status;
    request.reviewedBy = user.sub;
    request.reviewedByName = `${user.firstName} ${user.lastName}`;
    request.reviewedOn = new Date();
    request.reviewNotes = notes;

    return res.json({
      message: 'Request reviewed.',
      request
    });
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'Request review failure.',
      error
    });
    next(error);
  }
}