"use strict";
const { Model } = require("sequelize");
module.exports = (sequelize, DataTypes) => {
  class Schedule extends Model {
    static associate(models) {
      Schedule.belongsTo(models.Driver, {
        foreignKey: "driver_id",
        as: 'driver',
      });
    }
  }
  Schedule.init(
    {
      driver_id: {
        type: DataTypes.BIGINT,
        allowNull: false,
      },

      bus_id: {
        type: DataTypes.BIGINT,
        allowNull: false,
      },
      from: {
        allowNull: false,
        type: "TIMESTAMP",
      },
      to: {
        allowNull: false,
        type: "TIMESTAMP",
      },
    },
    {
      sequelize,
      freezeTableName: true,
      tableName: "schedules",
      modelName: "Schedule",
      paranoid: true,
    }
  );
  return Schedule;
};
