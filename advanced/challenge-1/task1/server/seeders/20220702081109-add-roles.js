"use strict";

module.exports = {
  async up(queryInterface, Sequelize) {
    try {
      const currentDate = new Date();
      await queryInterface.bulkInsert(
        "roles",
        [
          {
            role_name: "manager",
            role_description: "Manager Role",
            createdAt: currentDate,
            updatedAt: currentDate,
          },
          {
            role_name: "employee",
            role_description: "Employee Role",
            createdAt: currentDate,
            updatedAt: currentDate,
          },
        ],
        {}
      );
    } catch (error) {}
  },

  async down(queryInterface, Sequelize) {
    await queryInterface.bulkDelete("roles", null, {});
  },
};
