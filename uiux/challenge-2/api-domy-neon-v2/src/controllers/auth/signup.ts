import bcrypt from 'bcrypt';
import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

import { DbContext } from '../../data-store/factories';
import { User } from '../../models/user';
import AuthValidations from '../../validations/auth';
import Logger from '../../providers/logger';

const PasswordHashSaltRound = 10;

export default async function(req: Request, res: Response, next: NextFunction) {
  try {
    await Promise.all(AuthValidations.signup.map(v => v.run(req)));
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    
    const {
      firstName,
      lastName,
      username,
      password,
      birthDate,
      occupation
    } = req.body;
    
    const user = new User(firstName, lastName, new Date(Date.parse(birthDate)), occupation);
    user.username = username;
    user.password = await bcrypt.hash(password, PasswordHashSaltRound);

    const existingUser = await DbContext.users.findOne({
      username
    });

    if (existingUser) {
      return res.status(400).json({ errors: [{ msg: 'A user with the chosen username already exist.' }] });
    } else {
      await DbContext.users.insert(user);
    }

    return res.json({
      message: 'Registration completed successfully.'
    });
  } catch (error) {
    Logger.log({
      level: 'error',
      message: 'User registration failure.',
      error
    });
    next(error);
  }
}