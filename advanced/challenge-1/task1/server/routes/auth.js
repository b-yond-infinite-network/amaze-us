const express = require("express");
const router = express.Router();
const { signin } = require("../contollers/auth.controller");
const { body, validationResult } = require("express-validator");

router.post(
  "/auth/signin",
  body("username").isEmail(),
  body("password").isLength({
    min: 6,
  }),
  function (req, res) {
    // #swagger.tags = ['Auth']
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ errors: errors.array() });
    }
    signin(req, res);
  }
);

module.exports = router;
