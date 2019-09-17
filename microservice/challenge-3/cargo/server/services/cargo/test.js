const chai = require('chai')
const sinon = require('sinon')
const sinonChai = require('sinon-chai')
const expect = chai.expect
chai.use(sinonChai)

const cargo = require('./index')

describe('cargo', function () {
  let _mongo
  let collections = []
  let collection
  beforeEach(function () {
    todoItem = { text: 'text' }
    _Joi = {
      validate: () => {}
    }
    _mongo = {
      then: () => {},
      catch: () => {}
    }
    collection = {
      find: () => {},
      insertOne: () => {},
      deleteOne: () => {}
    }
    collections = [collection]
  })
  afterEach(function () {
    sinon.restore()
  })
  describe('getAll', function () {
    it('should return empty array', async function () {
      const todos = {
        toArray: () => {}
      }
      sinon.stub(todos, 'toArray').returns([])
      sinon.stub(collection, 'find').returns(todos)
      sinon.stub(_mongo, 'then').resolves(collections)
      sinon.stub(_mongo, 'catch').returns(null)

      const result = await cargo({ _mongo }).getAll()
      expect(_mongo.then).to.have.been.called
      expect(collection.find).to.have.been.called
    })
  })
})
