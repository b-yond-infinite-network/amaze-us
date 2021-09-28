import { Application } from "express";

import DataSeeder from "../data-store/db-seeder";
import { DbContext } from "../data-store/factories";

import AppMiddlewares from "../middlewares";
import AppRoutes from "../routes";
import ConfigManager from "./configuration";

export class AppBootstrap {
  public static init(express: Application) {
    ConfigManager.init();
    AppMiddlewares.register(express);
    AppRoutes.mount(express);
  }

  public static async postInit() {
    if (ConfigManager.appConfig.seedData) {
      await (new DataSeeder(DbContext)).seed();
    }
  }
}