const _ = require('lodash');
const chai = require('chai');
const request = require('supertest');
const server = require('../../app');
const assert = chai.assert;

describe('DELETE /cargo/:hashId', () => {
  context('For a valid request', () => {
    let itemToDelete;
    const itemToDeleteName = 'test-delete-cargo-item';
    before(() => {
      return request(server)
        .post('/cargo')
        .set('content-type', 'application/json')
        .send({ text: itemToDeleteName, loaded: true })
        .then(res => {
          itemToDelete = _.find(res.body, { text: itemToDeleteName });
        });
    });
    it('Should remove cargo item, return 200 and a list of existent items', () => {
      return request(server)
        .del(`/cargo/${itemToDelete._id}`)
        .send()
        .expect(200)
        .then(res => {
          assert.isArray(res.body);
          assert.isUndefined(_.find(res.body, { text: itemToDeleteName }));
        });
    });
  });

  context('For an ID that does not exist', () => {
    it('Should do something', () => {
      return request(server)
        .del(`/cargo/invalid-id`)
        .send()
        .expect(500)
        .then(res => {
          assert.deepEqual(res.body, { message: 'Internal error' });
        });
    });
  });
});