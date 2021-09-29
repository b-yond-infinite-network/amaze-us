import { check } from "express-validator";
import { BabyMakingRequestStatus } from "../models/baby-making-request";

export default {
  create: [
    check('plannedForYear').isInt().custom((value: number) => {
      const now = new Date().getFullYear();
      if (value < now) {
        throw new Error('The planned year must be this year or in the future.');
      }

      return true;
    }),
    check('notes').optional().isLength({
      max: 500,
    })
  ],
  review: [
    check('status').trim().notEmpty().isIn(Object.values(BabyMakingRequestStatus)),
    check('notes').optional().isLength({
      max: 500,
    })
  ],
};