const RemindersDAO = require('../app/RemindersDAO');
const MockMySql = require('./mocks/MockMySql');


describe('remindersDAO', () => {
  test('mock sanity', () => {
    expect(new MockMySql()).toHaveProperty('createPool');
    
    expect(new MockMySql().createPool()).toHaveProperty('query');

    const pool = new MockMySql([1,2,3]).createPool();
    
    pool.query('asdsad', (err, results) => {
      expect(results).toEqual([1,2,3]);
    })

    pool.query('', 'whatever', (err, results) => {
      expect(results).toEqual([1,2,3]);
    })

    new MockMySql([], 'error').createPool().query('', (err, results) => {
      expect(results).toEqual([]);
      expect(err).toEqual('error');
    })

  })

  test('accepts mocked mysql', () => {
    const mysql = new MockMySql();
    const reminders = RemindersDAO.create({mysql});
    reminders.query();
    expect(mysql.createPool).toHaveBeenCalled();
  })

  test('can query', () => {
    const reminders = RemindersDAO.create({mysql: new MockMySql([1,2,3])});
    expect.assertions(1);
    return expect(reminders.findAll()).resolves.toEqual([1,2,3]);
  })

  test('error handling', () => {
    const reminders = RemindersDAO.create({mysql: new MockMySql(null, 'error!')});
    expect.assertions(1);
    return expect(reminders.findAll()).rejects.toEqual('error!');
  })

  test('connection ends after query', () => {
    const mysql = new MockMySql();
    const connection = mysql.createConnection();
    const reminders = RemindersDAO.create({mysql});
    reminders.query();

    expect(connection.end).toHaveBeenCalled();
  })
});