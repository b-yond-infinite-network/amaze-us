const express = require("express");
const router = express.Router();

const passport = require("passport");
require("../config/passport")(passport);
const {
  createSchedule,
  driversSchedules,
  updateSchedule,
  deleteSchedule,
  busesSchedules,
  topDriversSchedules,
} = require("../contollers/schedules.controller");
const { body, param, validationResult, query } = require("express-validator");
// Create a new schedule
router.post(
  "/schedules/add",
  passport.authenticate("jwt", {
    session: false,
  }),
  body("driver_id").isNumeric(),
  body("bus_id").isNumeric(),
  body("from")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  body("to")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  function (req, res) {
    // #swagger.tags = ['Schedule']

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    createSchedule(req, res);
  }
);

// Update a Schedule
router.put(
  "/schedules/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  body("bus_id").isNumeric().optional({ nullable: true }),
  body("from")
    .custom((d) => new Date(d).getTime() > 0)
    .optional({ nullable: true })
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  body("to")
    .custom((d) => new Date(d).getTime() > 0)
    .optional({ nullable: true })
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  function (req, res) {
    // #swagger.tags = ['Schedule']

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    updateSchedule(req, res);
  }
);

// Delete a Schedule
router.delete(
  "/schedules/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  function (req, res) {
    // #swagger.tags = ['Schedule']
    deleteSchedule(req, res);
  }
);

// Get Drivers schedules
router.get(
  "/schedules/driver_schedules",
  passport.authenticate("jwt", {
    session: false,
  }),
  query("from")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  query("to")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  function (req, res) {
    // #swagger.tags = ['Schedule']

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    driversSchedules(req, res);
  }
);

// Get Buse schedules
router.get(
  "/schedules/bus_schedules",
  passport.authenticate("jwt", {
    session: false,
  }),
  query("from")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  query("to")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  function (req, res) {
    // #swagger.tags = ['Schedule']

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    busesSchedules(req, res);
  }
);

// Get Top Drivers schedules
router.get(
  "/schedules/top_drivers_schedules",
  passport.authenticate("jwt", {
    session: false,
  }),
  query("top").isString(),
  query("from")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  query("to")
    .custom((d) => new Date(d).getTime() > 0)
    .withMessage("from must be in correct format yyyy:mm:dd hh:mm:ss"),
  function (req, res) {
    // #swagger.tags = ['Schedule']

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    topDriversSchedules(req, res);
  }
);

module.exports = router;
