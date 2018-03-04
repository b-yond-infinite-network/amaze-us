// @NamedQuery(name = "Reminders.findAll", query = "SELECT r FROM Reminders r")
//     , @NamedQuery(name = "Reminders.findById", query = "SELECT r FROM Reminders r WHERE r.id = :id")
//     , @NamedQuery(name = "Reminders.findByName", query = "SELECT r FROM Reminders r WHERE r.name = :name")
//     , @NamedQuery(name = "Reminders.findByDate", query = "SELECT r FROM Reminders r WHERE r.date = :date")
//     , @NamedQuery(name = "Reminders.findByIsComplete", query = "SELECT r FROM Reminders r WHERE r.isComplete = :isComplete")})

const mysql = require('mysql');
const promisify = require('util.promisify');
const util = require('./util')(mysql.escapeId);

// connection.connect();

// connection.query('SELECT 1 + 1 AS solution', function (error, results, fields) {
//     if (error) throw error;
//     console.log('The solution is: ', results[0].solution);
// });

// connection.end();

class RemindersDAO {
  constructor(options){
    this.options = Object.assign({
      mysql: mysql
    }, options);
    this.connect();
  }

  connect(){
    // TODO: KMS for credentials
    this.connectionPool = this.options.mysql.createPool({
      host     : 'jax-rs-db.cce8xk4qewtv.us-east-1.rds.amazonaws.com',
      user     : 'root',
      password : '12345678',
      database : 'reminders'
    });

    this.query = promisify(this.connectionPool.query.bind(this.connectionPool));
  }

  findAll(filters){
    return this.query('SELECT * FROM reminders r WHERE ' 
      + util.filterByAllSql(filters), Object.values(filters));
  }

  findById(id){
    return this.query('SELECT * FROM reminders r WHERE r.id = ?', id);
  }

  findByName(name){
    return this.query('SELECT * FROM reminders r WHERE r.name = ?', name);
  }

  findByDate(date){
    return this.query('SELECT * FROM reminders r WHERE r.date = ?', date);
  }

  findByIsComplete(isComplete){
    return this.query('SELECT * FROM reminders r WHERE r.isComplete = ?', isComplete);
  }

  createNewReminder(reminder){
    return this.query('INSERT INTO reminders SET ?', reminder);
  }

  close(){
    this.connectionPool.end();
  }
}

module.exports = {
  create: (options) => new RemindersDAO(options)
};