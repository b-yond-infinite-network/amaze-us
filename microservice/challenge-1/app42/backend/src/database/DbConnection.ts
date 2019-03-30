import {Sequelize} from "sequelize-typescript";

const sequelize =  new Sequelize({
    database: "byond",
    dialect: "postgres",
    host: "localhost",
    modelPaths: [__dirname + "../entity"],
    password: "postgres",
    username: "postgres",
});

export default sequelize;
