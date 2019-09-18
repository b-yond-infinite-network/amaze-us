const express = require("express");
const router = express.Router();
const { cargo } = require("../services");

router.get("/", (req, res, next) => {
  cargo()
    .getAll()
    .then(todos => {
      res.json(todos);
    })
    .catch(err => {
      next(err);
    });
});
router.post("/", (req, res, next) => {
  const { body } = req;
  if (!body || !body.text) {
    next({ status: 400, message: "bad request" });
    return;
  }
  cargo()
    .add(body)
    .then(response => {
      res.json(response);
    })
    .catch(err => {
      next({ err: { status: 400, message: err } });
    });
});
router.delete("/:cargoId", (req, res, next) => {
  const { params } = req;
  if (!req.params) {
    next({ err: { status: 400, message: "bad request" } });
    return;
  }
  cargo()
    .remove(params)
    .then(response => {
      res.json(response);
    })
    .catch(err => {
      next({ err: { status: 400, message: err } });
    });
});
module.exports = router;
