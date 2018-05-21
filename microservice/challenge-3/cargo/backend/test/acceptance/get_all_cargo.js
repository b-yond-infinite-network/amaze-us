const _ = require('lodash');
const chai = require('chai');
const request = require('supertest');
const server = require('../../app');
const assert = chai.assert;

describe('GET /cargo', () => {
  context('For a valid request', () => {
    it('Should return 200 and a list of cargo items', () => {
      return request(server)
        .get('/cargo')
        .set('content-type', 'application/json')
        .send()
        .expect(200)
        .then(res => {
          assert.isArray(res.body);
        });
    });
  });
});