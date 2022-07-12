"use strict";
module.exports = {
  up: async (queryInterface, Sequelize) => {
    await queryInterface.createTable("role_permissions", {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.BIGINT,
      },
      role_id: {
        type: Sequelize.BIGINT,
        references: {
          model: "roles",
          key: "id",
        },
        onDelete: "CASCADE",
      },
      perm_id: {  
        type: Sequelize.BIGINT,
        references: {
          model: "permissions",
          key: "id",
        },

        onDelete: "CASCADE",
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
      },
    });
    await queryInterface.addIndex("role_permissions", ["role_id", "perm_id"], {
      unique: true,
    });
  },
  down: async (queryInterface, Sequelize) => {
    await queryInterface.dropTable("role_permissions");
  },
};
