var sinon = require('sinon');
var chai = require('chai');
var expect = chai.expect;
require('../app/models/cargoModel');
var cargoController = require('../app/controllers/cargoController');

describe("Cargo Controller Tests", function() {

  describe("list_all_Cargos", function() {
      it("should respond", function() {
        var req,res,spy;

        req = res = {};
        spy = res.send = sinon.spy();

        cargoController.list_all_Cargos(req, res);
        expect(spy.calledOnce).to.equal(false);
      });     
  });
});