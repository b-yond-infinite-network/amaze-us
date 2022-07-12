"use strict";

const _ = require("lodash");
const { faker } = require("@faker-js/faker");
const Driver = require("../models").Driver;
const Helper = require("../utils/helper");
const helper = new Helper();


module.exports = {
  up: async (queryInterface, Sequelize) => {
    const currentDate = new Date();
    var newData = [];
    var dataCount = 10;
    var drivers = await Driver.findAll({ attributes: ["id"] });
    var driversCount = drivers.length;

    var makes = ["2017", "2018", "2019", "2020", "2021"];
    var makesCount = makes.length;
    for (let i = 0; i < dataCount; i++) {
      const seedData = {
        capacity: helper.rand(20, 30), //[20,30]
        model: faker.vehicle.manufacturer(),
        make: makes[ helper.rand(0, makesCount - 1)],
        associated_driver_id: drivers[ helper.rand(0, driversCount - 1)].dataValues.id, //[0,driversCount]
        createdAt: currentDate,
        updatedAt: currentDate,
      };
      newData.push(seedData);
    }
    const chunks = _.chunk(newData, 1000);

    for (const chunk of chunks) {
      await queryInterface
        .bulkInsert("buses", chunk, {
          ignoreDuplicates: true,
        })
        .catch(console);
    }
  },

  down: (queryInterface) => {
    return queryInterface.bulkDelete("buses", null, {});
  },
};
