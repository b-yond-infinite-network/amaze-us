"use strict";
const Permission = require("../models").Permission;
module.exports = {
  async up(queryInterface, Sequelize) {
    try {
      const currentDate = new Date();
      const permissions = [
        {
          perm_name: "driver_schedules",
          perm_description: "Driver schedules",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "bus_schedules",
          perm_description: "Bus Schedules",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "top_drivers_schedules",
          perm_description: "Top Drivers Schedules",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        //Schedules
        {
          perm_name: "schedule_add",
          perm_description: "add schedule",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        {
          perm_name: "schedule_update",
          perm_description: "Update schedule",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "schedule_delete",
          perm_description: "Delete schedule",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        //USER
        {
          perm_name: "user_create",
          perm_description: "Add User",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        {
          perm_name: "user_update",
          perm_description: "Update User",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "user_delete",
          perm_description: "Delete User'",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        //Driver
        {
          perm_name: "driver_create",
          perm_description: "Add Driver",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        {
          perm_name: "driver_update",
          perm_description: "Update Driver",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "driver_delete",
          perm_description: "Delete Driver'",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        //Bus
        {
          perm_name: "bus_create",
          perm_description: "Add Bus",
          createdAt: currentDate,
          updatedAt: currentDate,
        },

        {
          perm_name: "bus_update",
          perm_description: "Update Bus",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
        {
          perm_name: "bus_delete",
          perm_description: "Delete Bus'",
          createdAt: currentDate,
          updatedAt: currentDate,
        },
      ];

      for (const permission of permissions) {
        try {
          await Permission.create(permission)
            .then((p) => p)
            .catch((error) => error);
        } catch (error) {}
      }
    } catch (error) {}
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("permissions", null, {});
  },
};
