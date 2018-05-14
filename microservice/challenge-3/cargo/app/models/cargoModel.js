var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CargoSchema = new Schema({
	text: {
	  type: String
	},
	loaded: {
		type: Boolean,
		default:false
	}
  });

module.exports = mongoose.model('Cargo', CargoSchema);