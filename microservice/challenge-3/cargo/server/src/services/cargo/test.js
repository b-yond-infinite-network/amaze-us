const chai = require("chai");
const sinon = require("sinon");
const sinonChai = require("sinon-chai");
const expect = chai.expect;
chai.use(sinonChai);

const cargo = require("./index");

describe("cargo", function() {
  let _mongo;
  let collections = [];
  let collection;
  let todoItem;
  beforeEach(function() {
    todoItem = { text: "text" };
    _mongo = {
      then: () => {},
      catch: () => {}
    };
    collection = {
      find: () => {},
      insertOne: () => {},
      deleteOne: () => {}
    };
    collections = [collection];
  });
  afterEach(function() {
    sinon.restore();
  });
  describe("getAll()", function() {
    it("should return empty array", async function() {
      const todos = {
        toArray: () => {}
      };
      sinon.stub(todos, "toArray").returns([]);
      sinon.stub(collection, "find").returns(todos);
      sinon.stub(_mongo, "then").resolves(collections);
      sinon.stub(_mongo, "catch").returns(null);
      await cargo({ _mongo }).getAll();
      expect(_mongo.then).to.have.been.called;
      expect(collection.find).to.have.been.called;
    });
  });
  describe("add()", function() {
    it(`should return a validation error if not a valid item is passed`, async function() {
      todoItem.text = 1234;
      sinon.stub(_mongo, "then").resolves(collections);
      sinon.stub(_mongo, "catch").returns(null);
      const [{ message }] = await cargo({ _mongo }).add(todoItem);
      expect(message).to.equal(`"text" must be a string`);
    });
  });
});
