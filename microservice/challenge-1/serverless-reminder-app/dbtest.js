const mysql      = require('mysql');
const connection = mysql.createConnection({
  host     : 'jax-rs-db.cce8xk4qewtv.us-east-1.rds.amazonaws.com',
  user     : 'root',
  password : '12345678',
  database : 'reminders'
});
const promisify = require('util.promisify');
const query = promisify(connection.query.bind(connection));


connection.connect();


// query('SELECT 1 + 1 AS solution')
query('SELECT * FROM reminders r')
    .then((obj) => {
        console.log(obj);
        console.log(obj[0].id);
    })
    .catch(e => {
        console.error(e);
    })

connection.end();