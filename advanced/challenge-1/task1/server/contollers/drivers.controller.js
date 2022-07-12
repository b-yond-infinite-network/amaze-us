const Driver = require("../models").Driver;
const Role = require("../models").Role;
const Helper = require("../utils/helper");
const helper = new Helper();

const addDriver = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "driver_create")
    .then(() => {
      Driver.create({
        first_name: req.body.first_name,
        last_name: req.body.last_name,
        email: req.body.email,
        social_security_number: req.body.social_security_number,
      })
        .then((obj) => res.status(201).send(obj))
        .catch((error) => {
          res.status(400).send("Dublicated Input");
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const updateDriver = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "driver_update")
    .then(() => {
      Driver.findByPk(req.params.id)
        .then((obj) => {
          if (!obj) {
            res.status(404).send({
              message: "Driver not found",
            });
          }
          Driver.update(
            {
              first_name: req.body.first_name ?? obj.first_name,
              last_name: req.body.last_name ?? obj.last_name,
              email: req.body.email ?? obj.email,
              social_security_number:
                req.body.social_security_number ?? obj.social_security_number,
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

const deleteDriver = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "driver_delete")
    .then(() => {
      if (!req.params.id) {
        res.status(400).send({
          msg: "Please pass id.",
        });
      } else {
        Driver.findByPk(req.params.id)
          .then((obj) => {
            if (obj) {
              Driver.destroy({
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
                message: "Driver not found",
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

module.exports = { updateDriver, deleteDriver, addDriver };
