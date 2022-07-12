const swaggerAutogen = require('swagger-autogen')()


const doc = {
    info: {
      version: '2.21.2',      // by default: '1.0.0'
      title: '',        // by default: 'REST API'
      description: '',  // by default: ''
    },
    host: '',      // by default: 'localhost:3000'
    basePath: '/api/v1',  // by default: '/'
    schemes: ['http'],   // by default: ['http']
    consumes: ['application/json'],  // by default: ['application/json']
    produces: ['application/json'],  // by default: ['application/json']
    tags: [        // by default: empty Array
      {
        name: '',         // Tag name
        description: '',  // Tag description
      },
      // { ... }
    ],
    securityDefinitions: {
        AuthToken: {
          "type": "apiKey",
          "name": "Authorization",
          "in": "header",
          "description": "The token for authentication"
        }
      },
     security: [
        {
          "AuthToken": []
        }
      ],
    definitions: {},          // by default: empty object (Swagger 2.0)
    components: {}            // by default: empty object (OpenAPI 3.x)
  };
const outputFile = './swagger_output.json'
const endpointsFiles = ['./routes/auth.js','./routes/schedules.js','./routes/users.js']

swaggerAutogen(outputFile, endpointsFiles,doc)

/**
 *   
 * "securityDefinitions": {
    "AuthToken": {
      "type": "apiKey",
      "name": "Authorization",
      "in": "header",
      "description": "The token for authentication"
    }
  },
 "security": [
    {
      "AuthToken": []
    }
  ],
 */