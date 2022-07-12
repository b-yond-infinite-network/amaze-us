"use strict";
const { Model } = require("sequelize");
const bcrypt = require("bcryptjs");

module.exports = (sequelize, DataTypes) => {
  class Bus extends Model {
    static associate(models) {
      Bus.hasMany(models.Schedule, {
        foreignKey: "bus_id",
        as: "schedules",
      });
    }
  }
  Bus.init(
    {
      capacity: {
        type: DataTypes.INTEGER,
        allowNull: false,
      },

      model: {
        type: DataTypes.STRING,
        allowNull: false,
      },
      make: {
        type: DataTypes.STRING,
        allowNull: false,
      },

      associated_driver_id: {
        type: DataTypes.BIGINT,
        allowNull: false,
      },
    },
    {
      sequelize,
      freezeTableName: true,
      tableName: "buses",
      modelName: "Bus",
      paranoid: true,
    }
  );

  return Bus;
};
