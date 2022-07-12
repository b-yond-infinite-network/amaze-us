"use strict";
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable("schedules", {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.BIGINT,
      },

      driver_id: {
        type: Sequelize.BIGINT,
        references: {
          model: "drivers",
          key: "id",
        },
      },

      bus_id: {
        type: Sequelize.BIGINT,
        references: {
          model: "buses",
          key: "id",
        },
      },
      from: {
        allowNull: false,
        type: "TIMESTAMP",
      },
      to: {
        allowNull: false,
        type: "TIMESTAMP",
        defaultValue:Sequelize.fn('NOW')
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      deletedAt: {
        allowNull: true,
        type: Sequelize.DATE,
      },
    });

    // await queryInterface.addIndex("driver_index", ["driver_id", "from","to"]);
    // await queryInterface.addIndex("bus_index", ["bus_id", "from","to"]);
  },
  async down(queryInterface, Sequelize) {
    await queryInterface.dropTable("schedules");
  },
};
