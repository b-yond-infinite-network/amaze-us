const User = require("../models").User;
const Schedule = require("../models").Schedule;
const Driver = require("../models").Driver;
const Bus = require("../models").Bus;
const Helper = require("../utils/helper");
const helper = new Helper();
const { Op, Sequelize } = require("sequelize");

const today = new Date();
const beforeYear = new Date(today.setFullYear(today.getFullYear() - 1));
const createSchedule = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "schedule_add")
    .then(() => {
      Schedule.create({
        driver_id: req.body.driver_id,
        bus_id: req.body.bus_id,
        from: req.body.from,
        to: req.body.to,
      })
        .then((obj) => {
          Driver.findByPk(req.body.driver_id).then((driver) => {
            helper
              .sendEmail(driver.email)
              .then(() => res.status(201).send(obj));
          });
        })
        .catch((error) => {
          res.status(400).send({
            success: false,
            msg: error,
          });
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const driversSchedules = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "driver_schedules")

    .then(() => {
      Driver.findAll({
        include: [
          {
            model: Schedule,
            as: "schedules",
            where: {
              from: {
                [Op.gte]: req.query.from,
              },
              to: {
                [Op.lte]: req.query.to,
              },
            },
            order: Sequelize.literal("from DESC"),
          },
        ],
      })

        .then((data) => res.status(200).send(data))
        .catch((error) => {
          res.status(400).send({
            success: false,
            msg: error,
          });
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const busesSchedules = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "bus_schedules")

    .then(() => {
      Bus.findAll({
        include: [
          {
            model: Schedule,
            as: "schedules",
            where: {
              from: {
                [Op.gte]: req.query.from,
              },
              to: {
                [Op.lte]: req.query.to,
              },
            },
            order: Sequelize.literal("from DESC"),
          },
        ],
      })

        .then((data) => res.status(200).send(data))
        .catch((error) => {
          res.status(400).send({
            success: false,
            msg: error,
          });
        });
    })
    .catch((error) => {
      res.status(403).send(error);
    });
};

const topDriversSchedules = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "top_drivers_schedules")

    .then(async () => {
      var result = [];
     
      var weekStart = new Date(req.query.from);
      var end = new Date(req.query.to);
      
      do {
        var next = new Date(weekStart.getTime());
        next.setHours(
          next.getHours() + 24 * 7 //1 week
        );
        var weekEnd = end < next ? end : next;
        // res.status(200).send(req);
        //query
        var drivers = await Schedule.findAll({
          where: {
            from: {
              [Op.gte]: weekStart,
            },
            to: {
              [Op.lt]: weekEnd,
            },
          },

          attributes: [
            [Sequelize.fn("COUNT", Sequelize.col("driver_id")), "scheduls_count"],
          ],
          group: "driver_id",
          order: Sequelize.literal("scheduls_count DESC"),

          limit: Number(req.query.top)??2,
          include: [{ model: Driver, as: "driver" }],
        });


        result.push({ from: weekStart, to: weekEnd, data: drivers });

        weekStart = weekEnd;
      } while (weekEnd < end);
      res.status(200).send(result);
    })
    .catch((error) => {
      console.log("error", error);
      res.status(403).send(error);
    });
};



const updateSchedule = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "schedule_update")
    .then(() => {
      Schedule.findOne({
        where: {
          id: req.params.id,
        },
        include: [{ model: Driver, as: "driver" }],
      })
        .then((schedule) => {
          if (!schedule) {
            res.status(404).send("Schedule not found");
          }

          Schedule.update(
            {
              bus_id: req.body.bus_id ?? schedule.bus_id,
              from: req.body.from ?? schedule.from,
              to: req.body.to ?? schedule.to,
            },
            {
              where: {
                id: req.params.id,
              },
            }
          )
            .then(() =>
              helper.sendEmail(schedule.driver.email).then(() => {
                res.status(200).send({
                  message: "updated",
                });
              })
            )
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

const deleteSchedule = (req, res) => {
  helper
    .checkPermission(req.user.role_id, "schedule_delete")
    .then(() => {
      if (!req.params.id) {
        res.status(400).send({
          msg: "Please pass  ID.",
        });
      } else {
        Schedule.findOne({
          where: {
            id: req.params.id,
          },
          include: [{ model: Driver, as: "driver" }],
        })
          .then((schedule) => {
            if (schedule) {
              Schedule.destroy({
                where: {
                  id: req.params.id,
                },
              })
                .then(() =>
                  helper.sendEmail(schedule.driver.email).then(() => {
                    res.status(200).send({
                      message: "deleted",
                    });
                  })
                )

                .catch((err) => res.status(400).send(err));
            } else {
              res.status(404).send({
                message: "Schedule not found",
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

module.exports = {
  createSchedule,
  updateSchedule,
  deleteSchedule,
  driversSchedules,
  busesSchedules,
  topDriversSchedules,
};
