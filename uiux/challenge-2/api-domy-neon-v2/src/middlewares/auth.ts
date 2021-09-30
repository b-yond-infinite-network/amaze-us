import { Application } from "express";
import passport from "passport";
import passportLocal, { IVerifyOptions } from 'passport-local';
import passportJWT from 'passport-jwt';

import { DbContext } from "../data-store/factories";
import logger from "../providers/logger";
import ConfigManager from "../providers/configuration";

const { Strategy: LocalStrategy } = passportLocal;
const { Strategy: JWTStrategy, ExtractJwt } = passportJWT;

async function doLogin(
  username: string,
  password: string,
  done: (error: any, user?: any, options?: IVerifyOptions) => void) {
  try {
    const user = await DbContext.users.findOne({
      username
    });

    if (!user) {
      logger.log({
        level: 'warn',
        message: 'Login attempt with unknown user',
        username
      });
      return done(null, false, { message: 'Invalid user credentials' });
    }

    const validate = await user.isValidPassword(password);
    if (!validate) {
      logger.log({
        level: 'warn',
        message: 'User login with invalid credentials',
        username
      });
      return done(null, false, { message: 'Invalid user credentials' });
    }

    return done(null, user, { message: 'Login successful' });
  } catch (error) {
    return done(error);
  }
}

export default class AuthMiddlewares {
  public static register(express: Application) {
    express.use(passport.initialize());

    passport.use(
      'login',
      new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password'
      },doLogin)
    );

    passport.use(
      new JWTStrategy(
        {
          secretOrKey: ConfigManager.appConfig.jwtSigningKey,
          jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
          jsonWebTokenOptions: {
            issuer: ConfigManager.appConfig.jwtTokenIssuer,
            audience: ConfigManager.appConfig.jwtTokenAudience,
          }
        },
        async (userTokenPayload, done) => {
          try {
            return done(null, userTokenPayload);
          } catch (error) {
            done(error);
          }
        }
      )
    );
  }
}