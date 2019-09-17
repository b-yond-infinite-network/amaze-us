const chai = require("chai");
const sinonChai = require("sinon-chai");
const expect = chai.expect;
chai.use(sinonChai);

const _mongo = require('../../lib/mongo/database');
const {cargo} = require('../../services');

describe(`Cargo`, function(){
  let dbConnection;
  let todos = [];
  let todoItem = { text:"item"}
  let todosCollection;
  let mongo ;
  before(async function(){
    mongo =_mongo({})
    dbConnection = await mongo.connect({})
    });
  after(async function() {
    await mongo.close()
  });

  afterEach(async function() {
    dbConnection.collection('todos').deleteMany({})
  })

  describe(`getAll()`,function(){
    it(`should return an empty array`, async function(){
      const {todos} = await cargo().getAll();
      expect(todos).to.have.length(0)
    })
  })
  describe(`add()`,function(){
    it(`should return an array with 2 todos`, async function(){
       const {todos:result} = await cargo().add(todoItem);
       const {todos} = await cargo().add(todoItem);
       expect(result).to.have.length(1)
       expect(todos).to.have.length(2)
    })

  })
  describe(`remove()`,function(){
    it(`shoudld return an array with length 1`, async function(){
      const firstAddResult = await cargo().add(todoItem);
      const {todos:secondAddResult} = await cargo().add(todoItem);
      let [{_id:cargoId}]= secondAddResult
      cargoId = JSON.stringify(cargoId);
      cargoId = JSON.parse(cargoId)
      const {todos} = await cargo().remove({cargoId})
      expect(todos).to.have.length(1)
    })

  })
})