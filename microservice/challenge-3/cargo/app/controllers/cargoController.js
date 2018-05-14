'use strict';

var mongoose = require('mongoose'),
Cargo = mongoose.model('Cargo');

exports.list_all_Cargos = function(req, res) {
    Cargo.find({}, function(err, cargo_list) {
    if (err)
      res.send(err);
    res.json(cargo_list);
  });
};

exports.create_a_cargo = function(req, res) {
  var new_cargo = new Cargo(req.body);
  new_cargo.save(function(err, cargo) {
    if (err)
      res.send(err);
      res.json({ message: 'Cardo successfully created' });
  });
};

exports.delete_a_task = function(req, res) {

    Cargo.remove({
        _id : req.params.cargo_id
  }, function(err, task) {
    if (err)
      res.send(err);
    res.json({ message: 'Cardo successfully deleted' });
  });
};