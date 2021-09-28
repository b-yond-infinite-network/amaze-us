import { check } from "express-validator";

const AuthValidations = {
  login: [
    check('username').trim().notEmpty(),
    check('password').trim().notEmpty(),
  ]
};

export default AuthValidations;