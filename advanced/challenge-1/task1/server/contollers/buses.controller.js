const Bus = require("../models").Bus;
const Role = require("../models").Role;
const Helper = require("../utils/helper");
const helper = new Helper();

const addBus = (req, res) => {
  helper.checkPermission(req.user.role_id, "bus_create").then(() => {
    Bus.create({
      capacity: req.body.capacity,
      model: req.body.model,
      make: req.body.make,
      associated_driver_id: req.body.associated_driver_id,
    })
      .then((obj) => res.status(201).send(obj))
      .catch((error) => {
        res.status(400).send("Dublicated Input");
      });
  })  .catch((error) => {
    res.status(403).send(error);
  });
};

const updateBus = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "bus_update")
    .then(() => {
      Bus.findByPk(req.params.id)
        .then((obj) => {
          if (!obj) {
            res.status(404).send({
              message: "Bus not found",
            });
          }
          Bus.update(
            {
              capacity: req.body.capacity ?? obj.capacity,
              model: req.body.model ?? obj.model,
              make: req.body.make ?? obj.make,
              associated_driver_id: req.body.associated_driver_id ?? obj.associated_driver_id,
            },
            {
              where: {
                id: req.params.id,
              },
            }
          )
            .then((_) => {
              res.status(200).send({
                message: "updated",
              });
            })
            .catch((err) => res.status(400).send(err.errors));
        })
        .catch((error) => {
          res.status(400).send(error.errors);
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const deleteBus = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "bus_delete")
    .then(() => {
      if (!req.params.id) {
        res.status(400).send({
          msg: "Please pass id.",
        });
      } else {
        Bus.findByPk(req.params.id)
          .then((obj) => {
            if (obj) {
              Bus.destroy({
                where: {
                  id: req.params.id,
                },
              })
                .then(() => {
                  res.status(200).send({
                    message: "deleted",
                  });
                })
                .catch((err) => res.status(400).send(err));
            } else {
              res.status(404).send({
                message: "Bus not found",
              });
            }
          })
          .catch((error) => {
            res.status(400).send(error);
          });
      }
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

module.exports = { updateBus, deleteBus, addBus };
