const SwaggerExpress = require('swagger-express-mw');
const express = require('express');
const app = express();
const swaggerUi = require('swagger-ui-express');
const YAML = require('yamljs');
const swaggerDocument = YAML.load('./api/swagger/swagger.yaml');

module.exports = app; // for testing

const config = {
  appRoot: __dirname // required config
};

SwaggerExpress.create(config, (err, swaggerExpress) => {
  if (err) { throw err; }

  // install middleware
  swaggerExpress.register(app);
  app.use('/docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

  const port = process.env.PORT || 3001;
  app.listen(port);
});
