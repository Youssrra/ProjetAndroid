const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');
const http = require('http');
import ('dotenv/config');
const errorHandler = require('./config/error_handler');
const jwt = require('./config/jwt');

var alertRoutes = require('./routes/alert');
var userRoutes = require('./routes/user') ;
var messageRoutes = require('./routes/message') ;
var commentRoutes = require('./routes/comment');
//var authen = require('./routes/auth');

const app = express() ;
const db = require("./models");
const Role = db.Role;
//require('./routes/auth')(app);
//require('./routes/user')(app);

app.use(bodyParser.urlencoded({ extended: true }));

app.use(bodyParser.json());
app.use(bodyParser.json({ limit: "30mb", extended: true}));
app.use(bodyParser.urlencoded({ limit: "30mb", extended: true}));
app.use(cors());

// database connection
const PORT = process.env.PORT || 3000 ;
const DB_CONNECTION= "mongodb://localhost:27017"
mongoose.connect(DB_CONNECTION, { useNewUrlParser: true, useUnifiedTopology: true })
.then(() => app.listen(PORT, ()=> { console.log('Server running on port : '+PORT) }
))
.catch((error) => console.log(error.message));

app.use(function(req, res, next) {
  if (req.headers && req.headers.authorization && req.headers.authorization.split(' ')[0] === 'JWT') {
    jsonwebtoken.verify(req.headers.authorization.split(' ')[1], 'RESTFULAPIs', function(err, decode) {
      if (err) req.user = undefined;
      req.user = decode;
      next();
    });
  } else {
    req.user = undefined;
    next();
  }
});

function initial() {
  Role.estimatedDocumentCount((err, count) => {
    if (!err && count === 0) {
      new Role({
        name: "user"
      }).save(err => {
        if (err) {
          console.log("error", err);
        }

        console.log("added 'user' to roles collection");
      });

      new Role({
        name: "moderator"
      }).save(err => {
        if (err) {
          console.log("error", err);
        }

        console.log("added 'moderator' to roles collection");
      });

      new Role({
        name: "admin"
      }).save(err => {
        if (err) {
          console.log("error", err);
        }

        console.log("added 'admin' to roles collection");
      });
    }
  });
}

const JWT_SECRET=process.env.JWT_SECRET;

//routes
app.use('/alert',alertRoutes);
app.use('/user',userRoutes);
app.use('/message',messageRoutes);
app.use('/comment',commentRoutes);
app.use(jwt());

require('./routes/auth.routes')(app);
require('./routes/user.routes')(app);


// global error handler
app.use(errorHandler);


app.get('/',(req,res) => {
   console.log.message('hey there')
});

app.use(function(req, res) {
  res.status(404).send({ url: req.originalUrl + ' not found' })
});



module.exports = app;