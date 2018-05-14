var app = require('../server'),
  chai = require('chai'),
  mongoose = require('mongoose');
  request = require('supertest');
var config = require('../config/index');
var expect= chai.expect ;
var cargo = { 
    text: 'create cargo' 
};

describe('Cargo Integration Tests', function() {
    before(function (done) {
        mongoose.connect(config.urlDb);
        const db = mongoose.connection;
        db.on('error', console.error.bind(console, 'connection error'));
        db.once('open', function() {
          console.log('We are connected to test database!');
          done();
        });
      });

    describe('#GET cargo', function() { 
        it('should get all cargo', function(done) { 
            request(app) .get('/api/cargos').end(function(err, res) { 
                expect(res.statusCode).to.equal(200); 
                expect(res.body).to.be.an('array'); 
                expect(res.body).to.be.empty; 
                done(); 
            }); 
        });
    });

    describe('## Create Cargo ', function() { 
        it('should create a cargo', function(done) { 
          request(app).post('/api/cargos').send(cargo).end(function(err, res) { 
            expect(res.statusCode).to.equal(200); 
            expect(res.body.message).to.equal('Cardo successfully created'); 
            task = res.body; 
            done(); 
          }); 
        }); 
    }); 

    //After all tests are finished drop database and close connection
    after(function(done){
        mongoose.connection.db.dropDatabase(function(){
        mongoose.connection.close(done);
        });
    });
});