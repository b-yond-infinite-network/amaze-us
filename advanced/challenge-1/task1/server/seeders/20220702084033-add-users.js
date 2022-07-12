"use strict";
const Role = require("../models").Role;
const User = require("../models").User;
module.exports = {
  async up() {
    await Role.findOne({
      where: {
        role_name: "manager",
      },
    }).then((role) => {
      User.create({
        username: "manager@test.com",
        password: "manager@123",
        fullname: "Manager User",
        role_id: role.id,
      }).catch();
    }) ;
    await Role.findOne({
      where: {
        role_name: "employee",
      },
    }).then((role) => {
      User.create({
        username: "employee@test.com",
        password: "employee@123",
        fullname: "User1",
        role_id: role.id,
      }).catch();
    });
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("users", null, {});
  },
};
