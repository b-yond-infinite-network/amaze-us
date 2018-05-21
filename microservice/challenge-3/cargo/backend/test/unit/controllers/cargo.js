const sinon = require('sinon');
const model = require('../../../api/models/cargo');
const controller = require('../../../api/controllers/cargo');
const utils = require('../../helpers/utils');
const { assert } = require('chai');

let sandbox, response, request, responseStub;
const genericError = [[500], [{ message: 'Internal error' }]];

describe('Cargo controller', () => {
  beforeEach(function () {
    sandbox = sinon.createSandbox();
    responseStub = sandbox.stub();
    response = utils.createResponseObject(responseStub);
    responseStub.returns(response);
  });
  afterEach(function () {
    sandbox.restore();
  });

  context('getAll function', () => {
    before(() => {
      request = {};
    });
    it('Should call model find function and return data', () => {
      const modelResult = { text: 'test-result' };
      const mockModel = sandbox.mock(model)
        .expects('find')
        .once()
        .resolves(modelResult);
      return controller.getAll(request, response)
        .then(() => {
          mockModel.verify();
          assert.deepEqual(responseStub.args, [[modelResult]]);
      });
    });

    it('Should return 500 if model throws an error', () => {
      const mockModel = sandbox.mock(model)
        .expects('find')
        .once()
        .rejects('test error');
      return controller.getAll(request, response)
        .then(() => {
          mockModel.verify();
          assert.deepEqual(responseStub.args, genericError);
        });
    });
  });

  context('create function', () => {
    before(() => {
      request = { body: { text: 'test' } };
    });
    it('Should call model create function and return data', () => {
      const modelResult = { text: 'test-result' };
      const mockModelCreate = sandbox.mock(model)
        .expects('create')
        .withArgs({ text: 'test', loaded: false })
        .once()
        .resolves({});
      const mockModelFetch = sandbox.mock(model)
        .expects('find')
        .once()
        .resolves(modelResult);
      return controller.create(request, response)
        .then(() => {
          mockModelCreate.verify();
          mockModelFetch.verify();
          assert.deepEqual(responseStub.args, [[201], [modelResult]]);
      });
    });

    it('Should return 500 if model throws an error', () => {
      const mockModelCreate = sandbox.mock(model)
        .expects('create')
        .withArgs({ text: 'test', loaded: false })
        .once()
        .rejects('test error');
      const mockModelFetch = sandbox.mock(model)
        .expects('find')
        .never();
      return controller.create(request, response)
        .then(() => {
          mockModelCreate.verify();
          mockModelFetch.verify();
          assert.deepEqual(responseStub.args, genericError);
        });
    });
  });

  context('delete function', () => {
    const itemId = 1;
    before(() => {
      request = { swagger: { params: { hashId: { value: itemId } } } };
    });
    it('Should call model remove function and return data', () => {
      const modelResult = { text: 'test-result' };
      const mockModelRemove = sandbox.mock(model)
        .expects('remove')
        .withArgs({ _id: itemId })
        .once()
        .resolves({});
      const mockModelFetch = sandbox.mock(model)
        .expects('find')
        .once()
        .resolves(modelResult);
      return controller.delete(request, response)
        .then(() => {
          mockModelRemove.verify();
          mockModelFetch.verify();
          assert.deepEqual(responseStub.args, [[modelResult]]);
      });
    });

    it('Should return 500 if model throws an error', () => {
      const mockModelRemove = sandbox.mock(model)
        .expects('remove')
        .withArgs({ _id: itemId })
        .once()
        .rejects('test error');
      const mockModelFetch = sandbox.mock(model)
        .expects('find')
        .never();
      return controller.delete(request, response)
        .then(() => {
          mockModelRemove.verify();
          mockModelFetch.verify();
          assert.deepEqual(responseStub.args, genericError);
        });
    });
  });
});