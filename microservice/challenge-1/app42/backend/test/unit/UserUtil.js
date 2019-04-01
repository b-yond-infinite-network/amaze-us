const chai = require("chai");
const should = require('chai').should();
const expect = chai.expect;
const assert = chai.assert;

const userUtil = require("../../dist/src/utility/UserUtil");

describe("UserUtil", () => {

  it("toJson returns a json object", done => {
    const data = {email: "EAMIL", name: "NAME", id: "0", description: "DESC"};
    const jsonResult = userUtil.default.toJson(data);
    jsonResult.should.have.property("id");
    done();
  });

  it("isValid returns true", done => {
    const data = {email: "EAMIL", name: "NAME", id: "0", description: "DESC"};
    const isVaild = userUtil.default.isValid(data);
    assert.equal(isVaild, true);
    done();
  });

});
