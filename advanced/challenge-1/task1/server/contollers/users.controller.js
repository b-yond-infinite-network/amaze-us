const User = require("../models").User;
const Role = require("../models").Role;
const Helper = require("../utils/helper");
const helper = new Helper();

const addUser = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "user_create")
    .then(() => {
      Role.findOne({
        where: {
          role_name: "employee",
        },
      })
        .then((role) => {
          User.create({
            username: req.body.username,
            password: req.body.password,
            fullname: req.body.fullname,

            role_id: role.id,
          })
            .then((user) => res.status(201).send({ id: user.id }))
            .catch((error) => {
              res.status(400).send("Dublicated Username");
            });
        })
        .catch((error) => {
          res.status(400).send(error);
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const updateUser = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "user_update")
    .then(() => {
      User.findByPk(req.params.id)
        .then((user) => {
          if (!user) {
            res.status(404).send({
              message: "User not found",
            });
          }
          User.update(
            {
              fullname: req.body.fullname ?? user.fullname,

              role_id: req.body.role_id ?? user.role_id,
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
            .catch((err) => res.status(400).send(err));
        })
        .catch((error) => {
          res.status(400).send(error);
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const deleteUser = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "user_delete")
    .then(() => {
      if (!req.params.id) {
        res.status(400).send({
          msg: "Please pass user ID.",
        });
      } else {
        User.findByPk(req.params.id)
          .then((user) => {
            if (user) {
              User.destroy({
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
                message: "User not found",
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

module.exports = { updateUser, deleteUser, addUser };
