"use strict";
const Role = require("../models").Role;
const User = require("../models").User;
const Permission = require("../models").Permission;
const RolePermission = require("../models").RolePermission;
module.exports = {
  async up() {
    try {
      const userPermissionsNames = [
        "driver_schedules",
        "bus_schedules",
        "top_drivers_schedules",
      ];

      let mangerPermissionsNames = [
        "user_create",
        "user_update",
        "user_delete",

        "bus_create",
        "bus_update",
        "bus_delete",

        "driver_create",
        "driver_update",
        "driver_delete",

        "schedule_add",
        "schedule_update",
        "schedule_delete",
      ];
      mangerPermissionsNames =
        mangerPermissionsNames.concat(userPermissionsNames); //add user permissions to manger

      //admin
      const managerRole = await Role.findOne({
        where: {
          role_name: "manager",
        },
      })
        .then((role) => role)
        .catch((error) => error);
      const adminPermisions = await Permission.findAll({
        where: {
          perm_name: mangerPermissionsNames,
        },
      })
        .then((permisions) => permisions)
        .catch((error) => {});
      for (const permission of adminPermisions) {
        try {
          await RolePermission.create({
            role_id: managerRole.id,
            perm_id: permission.id,
          })
            .then((rp) => rp)
            .catch((error) => error);
        } catch (error) {}
      }

      //employee
      const empRole = await Role.findOne({
        where: {
          role_name: "employee",
        },
      })
        .then((role) => role)
        .catch((error) => error);
      const staffPermisions = await Permission.findAll({
        where: {
          perm_name: userPermissionsNames,
        },
      })
        .then((permisions) => permisions)
        .catch((error) => {});
      for (const permission of staffPermisions) {
        try {
          await RolePermission.create({
            role_id: empRole.id,
            perm_id: permission.id,
          })
            .then((rp) => rp)
            .catch((error) => error);
        } catch (error) {}
      }
    } catch (error) {
      // next(err);
    }
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("role_permissions", null, {});
  },
};
