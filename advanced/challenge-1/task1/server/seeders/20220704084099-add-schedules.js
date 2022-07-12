"use strict";

const _ = require("lodash");
const { faker } = require("@faker-js/faker");
const Driver = require("../models").Driver;
const Bus = require("../models").Bus;
const Helper = require("../utils/helper");
const helper = new Helper();

module.exports = {
  up: async (queryInterface, Sequelize) => {
    const currentDate = new Date();
    var newData = [];

    var dataCount = 10;//1000000;
    var drivers = await Driver.findAll({ attributes: ["id"] });
    var driversCount = drivers.length;

    var buses = await Bus.findAll({ attributes: ["id"] });
    var busesCount = buses.length;

    var startDat = "2022-07-01";
    var endDat = "2022-10-01";

    var minScheduleHours = 4;
    var maxScheduleHours = 8;

    for (let i = 0; i < dataCount; i++) {
      var from = helper.randomDate(new Date(startDat), new Date(endDat), 0, 24);
      var to = new Date(from.getTime());
      to.setHours(
        to.getHours() + helper.rand(minScheduleHours, maxScheduleHours)
      );

      const seedData = {
        driver_id: drivers[helper.rand(0, driversCount - 1)].dataValues.id,
        bus_id: buses[helper.rand(0, busesCount - 1)].dataValues.id,
        from: from,
        to: to,
        createdAt: currentDate,
        updatedAt: currentDate,
      };
      newData.push(seedData);
    }
    const chunks = _.chunk(newData, 1000);

    for (const chunk of chunks) {
      await queryInterface
        .bulkInsert("schedules", chunk, {
          ignoreDuplicates: true,
        })
        .catch(console);
    }
  },

  down: (queryInterface) => {
    return queryInterface.bulkDelete("schedules", null, {});
  },
};
