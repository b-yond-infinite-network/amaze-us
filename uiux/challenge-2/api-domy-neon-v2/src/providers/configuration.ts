import * as dotenv from 'dotenv';

interface AppConfig {
  logLevel: string;
  jwtSigningKey: string;
  jwtTokenAudience: string;
  jwtTokenIssuer: string;
  jwtTokenLifetime: string;
  databaseName: string;
};

class ConfigManager {
  private static _appConfig: AppConfig;

  public static get appConfig() : AppConfig {
    return ConfigManager._appConfig;
  }

  public static init() {
    dotenv.config();

    ConfigManager._appConfig = {
      jwtSigningKey: process.env.JWT_SIGNING_KEY || '',
      jwtTokenAudience: process.env.JWT_TOKEN_AUDIENCE || '',
      jwtTokenIssuer: process.env.JWT_TOKEN_ISSUER || '',
      jwtTokenLifetime: process.env.JWT_TOKEN_LIFETIME || '10m',
      logLevel: process.env.LOG_LEVEL || 'info',
      databaseName: process.env.DB_NAME || ''
    };
  }
}

export default ConfigManager;