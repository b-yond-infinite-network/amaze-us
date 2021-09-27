import { check } from "express-validator";

const AuthValidations = {
  signup: [
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
    check('birthDate').trim().notEmpty().isNumeric().isInt({
      min: 0,
    }).custom((value) => {
      const now = new Date().getTime();
      if (value > now) {
        throw new Error('The birth date must be in the past.');
      }

      return true;
    }),
  ]
};

export default AuthValidations;