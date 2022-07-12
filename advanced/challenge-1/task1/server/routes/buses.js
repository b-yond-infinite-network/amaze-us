const express = require("express");
const router = express.Router();
const passport = require("passport");
require("../config/passport")(passport);
const {
  addBus,
  updateBus,
  deleteBus,
} = require("../contollers/buses.controller");
const { body, validationResult } = require("express-validator");
// Update a Bus
router.post(
  "/buses/add",
  passport.authenticate("jwt", {
    session: false,
  }),

  body("capacity").isNumeric(),
  body("model").isString(),
  body("make"),
  body("associated_driver_id").isNumeric().optional({ nullable: true }),
  function (req, res) {
    /**
       #swagger.tags = ['Bus']
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    addBus(req, res);
  }
);

// Update a Bus
router.put(
  "/buses/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  body("capacity").isNumeric().optional({ nullable: true }),
  body("model").isString().optional({ nullable: true }),
  body("make").isString().optional({ nullable: true }),
  body("associated_driver_id").isNumeric().optional({ nullable: true }),

  function (req, res) {
    /**
       #swagger.tags = ['Bus']
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    updateBus(req, res);
  }
);

// Delete a Bus
router.delete(
  "/buses/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  function (req, res) {
    // #swagger.tags = ['Bus']
    deleteBus(req, res);
  }
);

module.exports = router;
