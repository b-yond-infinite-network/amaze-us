const request = require('supertest');
const MockMySql = require('./mocks/MockMySql');
const mysqlInstance = new MockMySql();
const remindersResource = require('../remindersResource');

describe('remindersResource', () => {
    const remindersCaller = request(remindersResource({mysql: mysqlInstance}));

    test('supports supertest', () => {
        return remindersCaller.get('/').then(res => {
            expect(res.statusCode).toBe(200);
        })
    })

    test('can get reminders', () => {
        const data = [1,2,3];
        mysqlInstance.setData(data);
        return remindersCaller.get('/reminders').then(res => {
            expect(res.statusCode).toBe(200);
            //console.log(res);
            expect(res.body).toEqual(data);
        })
    })
})