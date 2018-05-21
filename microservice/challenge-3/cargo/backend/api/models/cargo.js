const config = require('config');
const mongoose = require('mongoose');
mongoose.Promise = Promise;
mongoose.connect(config.database.host);

module.exports = mongoose.model('Cargo', {
	text : String,
	loaded : Boolean
});