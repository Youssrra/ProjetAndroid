const config = require("../config/auth.config");
const db = require("../models");
const email = require("../config/email.js");
const verPhone = require("../config/phoneVerification.js");
const User = db.user;
const Role = db.role;

var jwt = require("jsonwebtoken");
var bcrypt = require("bcryptjs");

exports.signup = (req, res) => {
  
  const user = new User({
    username: req.body.username,
    email: req.body.email,
    first_name : req.body.first_name,
    last_name : req.body.last_name,
    num_tel : req.body.num_tel,
    contact : req.body.contact,
    addresses_p :  req.body.addresses_p,
    password: bcrypt.hashSync(req.body.password, 8)
  });

  user.save((err, user) => {
    if (err) {
      res.status(500).send({ message: err });
      return;
    }

    if (req.body.roles) {
      Role.find(
        {
          name: { $in: req.body.roles }
        },
        (err, roles) => {
          if (err) {
            res.status(500).send({ message: err });
            return;
          }

          user.roles = roles.map(role => role._id);
          user.save(err => {
            if (err) {
              res.status(500).send({ message: err });
              return;
            }
          
            res.send({ message: "User was registered successfully!" });
          });
        }
      );
    } else {
      Role.findOne({ name: "user" }, (err, role) => {
        if (err) {
          res.status(500).send({ message: err });
          return;
        }

        user.roles = [role._id];
        user.save(err => {
          if (err) {
            res.status(500).send({ message: err });
            return;
          }
          
          res.send({ message: "User was registered successfully!" });
        });
      });
    }
  });
};

exports.verifyEmail = (req,res) =>{
 
  User.findOne({email: req.params.email}, function(err, user){
    if(err) {
      console.log(err);
      res.status(500).send({ message: err });
      return;
    }
    var message;
    if(user) {
      console.log(user)
        message = "email exists";
        console.log(message)
        res.status(500).send({ message});
        return;
    } else {
        message= "user doesn't exist";
        console.log(message)
        res.status(200).send({ message });
        return;
    }
    res.json({message: message});
});
};

exports.verifyUsername = (req,res) => {
 User.findOne({username: req.params.username}, function(err, user){
    if(err) {
      console.log(err);
      res.status(500).send({ message: err });
      return;
    }
    var message;
    if(user) {
      console.log(user)
        message = "username exists";
        console.log(message)
        res.status(500).send({ message});
        return;
    } else {
        message= "username doesn't exist";
        console.log(message)
        res.status(200).send({ message });
        return;
    }
}); 
};

exports.verifyPhone = (req,res) => {
  User.findOne({num_tel: req.params.num_tel}, function(err, user){
     if(err) {
       console.log(err);
       res.status(500).send({ message: err });
       return;
     }
     var message;
     if(user) {
       console.log(user)
         message = "phone exists";
         console.log(message)
         res.status(500).send({ message});
         return;
     } else {
         message= "phone doesn't exist";
         console.log(message)
         res.status(200).send({ message });
         return;
     }
     res.json({message: message});
 }); 
 };


exports.signin = (req, res) => {
  User.findOne({
    email: req.body.email
  })
    .populate("roles", "-__v")
    .exec((err, user) => {
      if (err) {
        res.status(500).send({ message: err });
        return;
      }

      if (!user) {
        return rres.status(400).json({
          success: false,
          msg: "User does not exists, go and register to continue"
          });
      }

      var passwordIsValid = bcrypt.compareSync(
        req.body.password,
        user.password
      );

      if (!passwordIsValid) {
        return res.status(400).json({
          success: false,
          accessToken: null,
          msg: "Invalid password"
          });
      }

      var token = jwt.sign({ id: user.id }, config.secret, {
        expiresIn: 86400 // 24 hours
      });

      var authorities = [];
      
      for (let i = 0; i < user.roles.length; i++) {
        authorities.push("ROLE_" + user.roles[i].name.toUpperCase());
      }
      

      user.email = 
      res.status(200).json({
        user: user,
        accessToken: token
        });
    });
  }

  exports.sendCode = (req, res) => {
    var phone = req.body.phone ;
    mycode = verPhone.randomString(6);
  
  console.log('phone : ' + phone);
  console.log('mycode : ' + mycode);
  
  verPhone.phoneVer(mycode,phone);
  return res.status(200).json({
    success: true,
    code : mycode
    });
  
  }

  // GET BY ID
exports.getUser = (req, res) => {
  const email = req.params.email;

  User.findOne({email: req.params.email }) 
  .exec((err, user) => {
    if (err) {
      res.status(500).send({ message: err });
      return;
    }
    else 
    res.status(200).json({
      user: user
      });


});

/*
exports.sendCode = (req, res) => {
  var username = req.body.username ;
  var emailsend = req.body.email ;


mycode = email.randomString(6);

console.log('username : ' + username);
console.log('mycode : ' + emailsend);
console.log('mycode : ' + mycode);

email.sendemail(username,mycode,emailsend);
return res.status(200).json({
  success: true,
  code : mycode
  });

}
*/



};