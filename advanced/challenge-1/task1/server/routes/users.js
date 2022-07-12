const express = require("express");
const router = express.Router();
const passport = require("passport");
require("../config/passport")(passport);
const {
  addUser,
  updateUser,
  deleteUser,
} = require("../contollers/users.controller");
const { body, validationResult } = require("express-validator");
// Update a User
router.post(
  "/users/add",
  passport.authenticate("jwt", {
    session: false,
  }),
  body("username").isEmail(),
  body("password").isLength({
    min: 6,
  }),
  body("fullname").isLength({
    min: 2,
  }),
  function (req, res) {
    /**
       #swagger.tags = ['User']
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    addUser(req, res);
  }
);

// Update a User
router.put(
  "/users/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  body("role_id").isNumeric().optional({ nullable: true }),
  body("fullname")
    .isLength({
      min: 2,
    })
    .optional({ nullable: true }),
  function (req, res) {
    /**
       #swagger.tags = ['User']
      #swagger.parameters['id'] = {
                description: 'User ID.',
                value:1
            }
    */

    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    updateUser(req, res);
  }
);

// Delete a User
router.delete(
  "/users/:id",
  passport.authenticate("jwt", {
    session: false,
  }),
  function (req, res) {
    // #swagger.tags = ['User']
    deleteUser(req, res);
  }
);

module.exports = router;
