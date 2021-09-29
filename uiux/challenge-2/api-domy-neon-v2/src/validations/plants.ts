import { check } from "express-validator";

export default {
  create: [
    check('seedId').trim().notEmpty(),
    check('seedUnits').notEmpty().isInt({
      min: 1,
    }),
    check('notes').optional().isLength({
      max: 500
    })
  ],
  harvest: [
    check('plantId').trim().notEmpty(),
    check('storageUnitId').trim().notEmpty(),
  ]
};
