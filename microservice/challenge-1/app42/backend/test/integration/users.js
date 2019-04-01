const chai = require("chai");
const chaiHttp = require("chai-http");
const should = require('chai').should();
const expect = chai.expect;
const assert = chai.assert;

const config = require("../../src/config");

chai.use(chaiHttp);

const API_URL = `http://localhost:${config.server.port}/users`;

describe("/users", () => {

  it("GET returns an array", done => {
    chai.request(API_URL).get("/").end((error, response) => {
      if (error) return done(error);
      assert.equal(Array.isArray(response.body), true);
      assert.equal(response.body.length, 0);
      done();
    });
  });

  it("POST adds a new user", done => {
    const postData = {
      name: "NAME",
      email: "EMAIL",
      description: "DESCRIPTION"
    };
    chai.request(API_URL).post("/").send(postData).end((error, response) => {
      if (error) return done(error);
      assert.equal(response.body.email, postData.email);
      response.body.should.have.property("id");
      done();
    });
  });

  it("GET returns non-zero length array", done => {
    chai.request(API_URL).get("/").end((error, response) => {
      if (error) return done(error);
      expect(response.body.length).to.not.equal(0);
      done();
    });
  });

});
