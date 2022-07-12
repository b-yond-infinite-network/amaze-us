const express = require("express");
const router = express.Router();
const passport = require("passport");
require("../config/passport")(passport);
const {
  addDriver,
  updateDriver,
  deleteDriver,
} = require("../contollers/drivers.controller");
const { body, validationResult } = require("express-validator");
// Update a Driver
router.post(
  "/drivers/add",
  passport.authenticate("jwt", {
    session: false,
  }),

  body("first_name").isLength({
    min: 2,
  }),
  body("last_name").isLength({
    min: 2,
  }),
  body("email").isEmail(),
  body("social_security_number").isString(),
  function (req, res) {
    /**
       #swagger.tags = ['Driver']
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    addDriver(req, res);
  }
);

// Update a Driver
router.put(
  "/drivers/:id",
  passport.authenticate("jwt", {
    session: false,
  }),

  body("first_name")
    .isLength({
      min: 2,
    })
    .optional({ nullable: true }),
  body("last_name").isLength({
    min: 2,
  }).optional({ nullable: true }),
  body("email").isEmail().optional({ nullable: true }),
  body("social_security_number").isString().optional({ nullable: true }),
  function (req, res) {
    /**
       #swagger.tags = ['Driver']
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    updateDriver(req, res);
  }
);

// Delete a Driver
router.delete(
  "/drivers/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  function (req, res) {
    // #swagger.tags = ['Driver']
    deleteDriver(req, res);
  }
);

module.exports = router;
