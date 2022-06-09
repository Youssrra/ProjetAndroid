const express = require('express');
const user = require('../controller/user.js');
const controller = require("../controller/user");
var config = require('../config/auth');
const { authJwt } = require("../middlewares");
const router = express.Router();
var jwt = require('jwt-simple');

router.get("/getAll", user.getUsers);
router.get("/getbyid/:id", user.getUserbyId);
router.get("/find", user.findUser);
router.post("/create", user.createUser);
//router.post("/register", user.registerr);
router.post("/login", user.login);
//router.post("/", user.loginRequired ,user.profile);
router.put("/update/:id", user.updateUser);
router.delete("/delete/:id", user.deleteUser);
router.delete("/deleteall", user.deleteAll);

// routes
router.post('/authenticate', user.authenticate);
router.post('/register', user.register);
router.get('/geta', user.getAll);
router.get('/current', user.getCurrent);
router.get('/:id', user.getById);
router.put('/:id', user.update);
router.delete('/:id', user._delete);
router.post('/email/:username', user.sendCode)


/*
module.exports = function(app) {
    app.use(function(req, res, next) {
      res.header(
        "Access-Control-Allow-Headers",
        "x-access-token, Origin, Content-Type, Accept"
      );
      next();
    });
  
    app.get("/api/test/all", controller.allAccess);
  
    app.get("/api/test/user", [authJwt.verifyToken], controller.userBoard);
  
    app.get(
      "/api/test/mod",
      [authJwt.verifyToken, authJwt.isModerator],
      controller.moderatorBoard
    );
  
    app.get(
      "/api/test/admin",
      [authJwt.verifyToken, authJwt.isAdmin],
      controller.adminBoard
    );
  };
*/
module.exports = router;
