import { check } from "express-validator";

const UserValidations = {
  create: [
    check('username').trim().notEmpty().isLength({
      min: 3,
      max: 32
    }),
    check('password').trim().notEmpty().isStrongPassword({
      minLength: 8,
      minLowercase: 1,
      minUppercase: 1,
      minNumbers: 1,
      minSymbols: 1,
    }),
    check('firstName').trim().notEmpty().isLength({
      min: 3,
      max: 50
    }),
    check('lastName').trim().notEmpty().isLength({
      min: 3,
      max: 50
    }),
    check('birthDate').trim().notEmpty().isDate({
      format: 'YYYY-MM-DD'
    }).custom((value) => {
      const now = new Date();
      if (new Date(value) > now) {
        throw new Error('The birth date must be in the past.');
      }
      return true;
    }),
  ],
};

export default UserValidations;