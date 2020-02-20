var Cargo = require('./models/cargo');

module.exports = function(app) {

	// api ---------------------------------------------------------------------
	// get all cargo items
	app.get('/api/cargo', function(req, res) {

		// use mongoose to get all todos in the database
        Cargo.find(function(err, cargo_list) {

			// if there is an error retrieving, send the error. nothing after res.send(err) will execute
			if (err)
				res.send(err);

			res.json(cargo_list); // return all cargo items in JSON format
		});
	});

	// create the cargo and send back all cargo items after creation
	app.post('/api/cargo', function(req, res) {

		// create a cargo, information comes from AJAX request from Angular
		Cargo.create({
			text : req.body.text,
			loaded : false
		}, function(err, cargo_item) {
			if (err)
				res.send(err);

			// get and return all the cargo items after you create another
			Cargo.find(function(err, cargo_list) {
				if (err)
					res.send(err);
				res.json(cargo_list);
			});
		});

	});

	// delete a todo
	app.delete('/api/cargo/:cargo_id', function(req, res) {
		Cargo.remove({
			_id : req.params.cargo_id
		}, function(err, cargo) {
			if (err)
				res.send(err);

			// get and return all the cargo items after you create another
			Cargo.find(function(err, cargo_list) {
				if (err)
					res.send(err);
				res.json(cargo_list);
			});
		});
	});

	// application -------------------------------------------------------------
	app.get('*', function(req, res) {
		res.sendfile('./public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
	});
};