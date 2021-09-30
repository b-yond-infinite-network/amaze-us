import { Request, Response, NextFunction } from "express";
import passport from "passport";
import { IVerifyOptions } from "passport-local";
import jwt from 'jsonwebtoken';
import { validationResult } from "express-validator";

import { User } from "../../models/user";
import ConfigManager from "../../providers/configuration";
import { DbContext } from "../../data-store/factories";
import Logger from "../../providers/logger";
import AuthValidations from "../../validations/auth";

export default async (req: Request, res: Response, next: NextFunction) => {
  await Promise.all(AuthValidations.login.map(v => v.run(req)));
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ validationErrors: errors.array() });
  }

  passport.authenticate(
    'login',
    async (err, user: User, options?: IVerifyOptions) => {
      try {
        if (err) {
          const error = new Error('An error occurred.');
          return next(error);
        }

        if (!user) {
          return res.json({ error: options?.message || '' });
        }

        req.login(
          user,
          { session: false },
          async (error) => {
            if (error) return next(error);
            const accessToken = jwt.sign({
              sub: user.id,
              firstName: user.firstName,
              lastName: user.lastName,
              occupation: user.occupation,
              groups: (await DbContext.userRoles.find({
                userId: user.id
              })).map(({ roleId }) => roleId)
            }, ConfigManager.appConfig.jwtSigningKey, {
              audience:  ConfigManager.appConfig.jwtTokenAudience,
              issuer: ConfigManager.appConfig.jwtTokenIssuer,
              expiresIn: ConfigManager.appConfig.jwtTokenLifetime,
            });

            return res.json({ access_token: accessToken });
          }
        );
      } catch (error) {
        Logger.log({
          level: 'error',
          message: 'Login failure',
          error
        });
        return next(error);
      }
    }
  )(req, res, next);
}