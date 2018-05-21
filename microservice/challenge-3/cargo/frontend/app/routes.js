module.exports = function(app) {
  app.get('/healthcheck.html', function(req, res) {
    res.send('OK');
  });
  // config loaded by environment
  app.get('/config', function(req, res) {
    res.json(require('config'));
  });

	// application -------------------------------------------------------------
	app.get('*', function(req, res) {
		res.sendfile('./public/index.html'); // load the single view file (angular will handle the page changes on the front-end)
	});
};