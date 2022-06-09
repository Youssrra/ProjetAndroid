const { verifySignUp } = require("../middlewares");
const controller = require("../controller/auth.controller");
module.exports = function(app) {
  app.use(function(req, res, next) {
    res.header(
      "Access-Control-Allow-Headers",
      "x-access-token, Origin, Content-Type, Accept"
    );
    next();
  });

  app.post(
    "/api/auth/signup",
    [
      verifySignUp.checkDuplicateUsernameOrEmail,
      verifySignUp.checkRolesExisted
    ],
    controller.signup
  );

  app.post("/api/auth/signin", controller.signin);

  app.post("/api/send",controller.sendCode);

  app.post("/api/verify/username/:username",controller.verifyUsername)

  app.get("/api/verify/email/:email",controller.verifyEmail)

  app.get("/api/verify/phone/:phone",controller.verifyPhone)

  app.get("/api/get/userByEmail/:email",controller.getUser)


};