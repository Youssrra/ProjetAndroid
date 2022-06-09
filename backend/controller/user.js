const db = require('../models/User.js');
var User = require('../models/User');
var bcrypt = require("bcryptjs");
var jwt = require("jsonwebtoken");
const userService = require('../service/user.service');


exports.allAccess = (req, res) => {
  res.status(200).send("Public Content.");
};
exports.userBoard = (req, res) => {
  res.status(200).send("User Content.");
};
exports.adminBoard = (req, res) => {
  res.status(200).send("Admin Content.");
};
exports.moderatorBoard = (req, res) => {
  res.status(200).send("Moderator Content.");
};


                        
// GET ALL
exports.getUsers = (req,res,next) => {
    db.find({}).then(function(user){
        res.send(user);
    }).catch(next);
    
};

// CREATE
exports.createUser = (req,res,next) => {

    if (!req.body) {
        res.status(400).send({ message: "Content can not be empty!" });
        return;
      }

    db.create(req.body).then(function(user){
        res.send(user);
    }).catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while creating the User."
        });
      }); 

}

//REGISTER
exports.registerr = function (req, res) {
  
  
 
};

//LOGIN
exports.login = function(req, res) {
  User.findOne({
    email: req.body.email
  }, function(err, user) {
    if (err) throw err;
    if (!user || !user.comparePassword(req.body.password)) {
      return res.status(401).json({ message: 'Authentication failed. Invalid user or password.' });
    }
    return res.json({ token: jwt.sign({ email: user.email, username: user.username, _id: user._id }, 'RESTFULAPIs') });
  });
};

//LOGIN ACCESS
exports.loginRequired = function(req, res, next) {
  if (req.user) {
    next();
  } else {

    return res.status(401).json({ message: 'Unauthorized user!!' });
  }
};

//PROFILE


// UPDATE
exports.updateUser =(req,res,next) => {
    
    if (!req.body) {
        return res.status(400).send({
          message: "Data to update can not be empty!"
        });
      }
    
      const id = req.params.id;
    
      db.findByIdAndUpdate(id, req.body, { useFindAndModify: false })
        .then(data => {
          if (!data) {
            res.status(404).send({
              message: `Cannot update user with id=${id}. Maybe this user dosn't exist!`
            });
          } else res.send({ message: "User was updated successfully." });
        })
        .catch(err => {
          res.status(500).send({
            message: "Error updating User with id=" + id
          });
        });
    };
    
// DELETE
exports.deleteUser = (req,res,next) => {
    db.findOneAndDelete({_id: req.params.id}).then(function(user){
        res.send(user);
    }).catch(err => {
        res
          .status(500)
          .send({ message: "Error while deleting user with id=" + req.params.id });
      });
};

// GET BY ID
exports.getUserbyId = (req, res) => {
    const id = req.params.id;
  
    db.findById(id)
      .then(data => {
        if (!data)
          res.status(404).send({ message: "User not found with id " + id });
        else res.send(data);
      })
      .catch(err => {
        res
          .status(500)
          .send({ message: "Error retrieving user with id=" + id });
      });
  };

  // FIND BY
  exports.findUser = (req, res) => {
    db.find({ like: 1 })
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving users."
        });
      });
  };

  // DELETE ALL
  exports.deleteAll = (req, res) => {
    db.deleteMany({})
      .then(data => {
        res.send({
          message: `${data.deletedCount} Users were deleted successfully!`
        });
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while removing all Users."
        });
      });
  };

  exports.authenticate = (req, res, next) => {
    userService.authenticate(req.body)
        .then(user => user ? res.json(user) : res.status(400).json({ message: 'Username or password is incorrect' }))
        .catch(err => next(err));
}

exports.register = (req, res, next) => {
    userService.create(req.body)
        .then(() => res.json({}))
        .catch(err => next(err));
}

exports.getAll = (req, res, next) => {
    userService.getAll()
        .then(users => res.json(users))
        .catch(err => next(err));
}

exports.getCurrent=(req, res, next) =>{
    userService.getById(req.user.sub)
        .then(user => user ? res.json(user) : res.sendStatus(404))
        .catch(err => next(err));
}

exports.getById = (req, res, next) => {
    userService.getById(req.params.id)
        .then(user => user ? res.json(user) : res.sendStatus(404))
        .catch(err => next(err));
}

exports.update = (req, res, next)=> {
    userService.update(req.params.id, req.body)
        .then(() => res.json({}))
        .catch(err => next(err));
}

exports._delete = (req, res, next)=> {
    userService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}

exports.allAccess = (req, res) => {
  res.status(200).send("Public Content.");
};

exports.userBoard = (req, res) => {
  res.status(200).send("User Content.");
};

exports.adminBoard = (req, res) => {
  res.status(200).send("Admin Content.");
};

exports.moderatorBoard = (req, res) => {
  res.status(200).send("Moderator Content.");
};
