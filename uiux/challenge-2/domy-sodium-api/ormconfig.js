module.exports = {
    type:     'postgres',
    username: process.env.POSTGRES_USERNAME || 'postgres',
    password: process.env.POSTGRES_PASSWORD || '12345',
    host:     process.env.POSTGRES_HOSTNAME || '127.0.0.1',
    port:     5432,
    database: process.env.POSTGRES_DATABASE || 'domy_sodium',
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
