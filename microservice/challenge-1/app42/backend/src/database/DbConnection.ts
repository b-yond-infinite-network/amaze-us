import {Sequelize} from "sequelize-typescript";

const sequelize = new Sequelize({
    database: "byond",
    dialect: "postgres",
    host: "localhost",
    modelPaths: [__dirname + "/../entity/"],
    password: "byond",
    port: 5431,
    username: "postgres",
});
export default sequelize;
