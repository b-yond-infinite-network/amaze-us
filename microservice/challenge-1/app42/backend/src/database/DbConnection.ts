import {Sequelize} from "sequelize-typescript";

const sequelize = new Sequelize({
    database: "byond",
    dialect: "mysql",
    host: "mysql",
    modelPaths: [__dirname + "/../entity/"],
    password: "root",
    username: "root",
});
export default sequelize;
