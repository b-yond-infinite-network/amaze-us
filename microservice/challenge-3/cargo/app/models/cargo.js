var mongoose = require('mongoose');

module.exports = mongoose.model('Cargo', {
	text : String,
	loaded : Boolean
});