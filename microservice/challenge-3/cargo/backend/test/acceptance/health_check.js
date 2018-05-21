const request = require('supertest');
const server = require('../../app');

describe('GET /healthcheck.html', () => {
  context('For a valid request', () => {
    it('Should return 200', () => {
      return request(server)
        .get('/healthcheck.html')
        .send()
        .expect(200);
    });
  });
});