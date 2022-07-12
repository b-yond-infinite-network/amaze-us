const jwt = require("jsonwebtoken");
const passport = require("passport");

require("../config/passport")(passport);
const User = require("../models").User;
const Role = require("../models").Role;
const Helper = require("../utils/helper");
const helper = new Helper();
const signin = (req, res) => {
  User.findOne({
    where: {
      username: req.body.username,
    },
  })
    .then((user) => {
      if (!user) {
        return res.status(401).send({
          message: "Authentication failed. User not found.",
        });
      }
      
      user.comparePassword(req.body.password, (err, isMatch) => {
        if (isMatch && !err) {
          var token = jwt.sign(
            JSON.parse(JSON.stringify(user)),
            "test-auth-secret",
            {
              expiresIn: 86400 * 30,
            }
          );
          jwt.verify(token, "test-auth-secret", function (err, data) {
            // console.log(err, data);
          });
        
        
          res.json({
   
            success: true,
            token: "JWT " + token,
            user:user
          
          });
        } else {
          res.status(401).send({
            success: false,
            msg: "Authentication failed. Wrong password.",
          });
        }
      });
    })
    .catch((error) => res.status(400).send(error));
};



module.exports = { signin };
