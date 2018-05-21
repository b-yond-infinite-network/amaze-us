const _ = require('lodash');
const chai = require('chai');
const request = require('supertest');
const server = require('../../app');
const assert = chai.assert;

describe('POST /cargo', () => {
  context('For a valid request', () => {
    it('Should insert data and return list of items', () => {
      const itemName = `new-cargo-test-item-${new Date().getTime()}`;
      return request(server)
        .post('/cargo')
        .set('content-type', 'application/json')
        .send({ text: itemName, loaded: true })
        .expect(201)
        .then(res => {
          assert.isArray(res.body);
          assert.isAbove(res.body.length, 1);
          const newItem = _.last(res.body);
          assert.deepEqual(newItem.text, itemName);
          assert.deepEqual(newItem.loaded, true);
          assert.isString(newItem._id);
        });
    });
  });

  context('For a request that fails swagger validation', () => {
    const failedSwaggerValidationResponse = {
      message: 'Request validation failed: Parameter (cargo) failed schema validation',
      code: 'SCHEMA_VALIDATION_FAILED',
      failedValidation: true,
      results: {
        errors: [{
          code: 'OBJECT_MISSING_REQUIRED_PROPERTY',
          message: 'Missing required property: text',
          path: []
        }],
        warnings: []
      },
      path: [ 'paths', '/cargo', 'post', 'parameters', '0' ],
      paramName: 'cargo'
    };
    it('Should return a 400 when the request fails swagger validation', () => {
      return request(server)
        .post('/cargo')
        .set('content-type', 'application/json')
        .send({ this: 'request-fails-swagger-validation' })
        .expect(400)
        .then(res => {
          assert.deepEqual(res.body, failedSwaggerValidationResponse);
        });
    });
  });
});