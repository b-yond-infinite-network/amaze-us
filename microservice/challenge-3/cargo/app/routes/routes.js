var Cargo = require('../models/cargoModel');

module.exports = function(app) {
	var cargoController = require('../controllers/cargoController');

	// cargo Routes
	app.route('/api/cargos')
		.get(cargoController.list_all_Cargos)
		.post(cargoController.create_a_cargo);

	app.route('/api/cargos/:cargo_id')
		.delete(cargoController.delete_a_task);
};