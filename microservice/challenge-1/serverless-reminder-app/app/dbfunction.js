exports.handler = function(event, context, callback) {
    console.log(event);
    context.callbackWaitsForEmptyEventLoop = false;
    var mysql      = require('mysql');
    const connection = mysql.createConnection({
        host     : 'jax-rs-db.cce8xk4qewtv.us-east-1.rds.amazonaws.com',
        user     : 'root',
        password : '12345678',
        database : 'reminders'
      });
    
    connection.query('SELECT * FROM reminders r', function(err, rows) {
        if (err) {
            console.error('error connecting: ' + err.stack);
            callback(null, err);
            return;
        }
        
        console.log('connected as id ' + connection.threadId);
        callback(null, rows);
    });

    connection.end();
};