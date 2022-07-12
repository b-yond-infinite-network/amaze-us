"use strict";
const Role = require("../models").Role;
const User = require("../models").User;
const Permission = require("../models").Permission;
const RolePermission = require("../models").RolePermission;
const _ = require("lodash");
const { faker } = require("@faker-js/faker");
var Chance = require("chance");

var chance = new Chance();
module.exports = {
  up: async (queryInterface, Sequelize) => {
    const currentDate = new Date();

    var newData = [];
    var dataCount = 10; //1000000;

    for (let i = 0; i < dataCount; i++) {
      const seedData = {
        first_name: faker.name.firstName(),
        last_name: faker.name.lastName(),
        social_security_number: chance.ssn(),
        email: faker.internet.email(),
         createdAt: currentDate,
            updatedAt: currentDate,
      };
      newData.push(seedData);
    }
    const chunks = _.chunk(newData, 1000);

    for (const chunk of chunks) {
      await queryInterface.bulkInsert("drivers", chunk, {
        ignoreDuplicates: true,
      });
    }
  },

  down: (queryInterface) => {
    return queryInterface.bulkDelete("drivers", null, {});
  },
};
