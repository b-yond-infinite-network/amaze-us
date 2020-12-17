const dotenv = require('dotenv');
const result = dotenv.config();

console.log(process.env.POSTGRES_DATABASE)

module.exports = {
    type:     'postgres',
    username: process.env.POSTGRES_USERNAME,
    password: process.env.POSTGRES_PASSWORD,
    host:     process.env.POSTGRES_HOSTNAME,
    port:     5432,
    database: process.env.POSTGRES_DATABASE,
    entities: [
        process.env.NODE_ENV === 'development' ? './src/entity/*.ts' : './build/entity/*.js'
    ],
    subscribers: [
        "build/subscribers/*.js"
    ],
    migrations: [
        "build/migrations/*.js"
    ],
    ssl: true,
    extra: {
        ssl: {
            rejectUnauthorized: false,
        },
    },
    synchronize: true,
    logging: false,
    bigNumberStrings: false,
    supportBigNumbers: true,
    synchronize: false
}
