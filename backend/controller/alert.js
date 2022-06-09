const db = require('../models/Alert.js');
const Alert = db.Alert ;


// GET ALL
exports.getAlerts = (req,res,next) => {

    db.find({}).then(function(alert){
        res.send(alert);
    }).catch(next);
    
};

// CREATE
exports.createAlert = (req,res,next) => {

    if (!req.body.title) {
        res.status(400).send({ message: "Content can not be empty!" });
        return;
      }

    db.create(req.body).then(function(alert){
        res.send(alert);
    }).catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while creating the Alert."
        });
      });

}

// UPDATE
exports.updateAlert =(req,res,next) => {
    
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
              message: `Cannot update alert with id=${id}. Maybe this alert dosn't exist!`
            });
          } else res.send({ message: "Alert was updated successfully." });
        })
        .catch(err => {
          res.status(500).send({
            message: "Error updating alert with id=" + id
          });
        });
    };
    
// DELETE
exports.deleteAlert = (req,res,next) => {
    db.findOneAndDelete({_id: req.params.id}).then(function(alert){
        res.send(alert);
    }).catch(err => {
        res
          .status(500)
          .send({ message: "Error while deleting alert with id=" + req.params.id });
      });
};

// GET BY ID
exports.getAlertbyId = (req, res) => {
    const id = req.params.id;
  
    db.findById(id)
      .then(data => {
        if (!data)
          res.status(404).send({ message: "Alert not found with id " + id });
        else res.send(data);
      })
      .catch(err => {
        res
          .status(500)
          .send({ message: "Error retrieving alert with id=" + id });
      });
  };

  // FIND BY
  exports.findAlert = (req, res) => {
    db.find({ like: 1 })
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving alerts."
        });
      });
  };

  // DELETE ALL
  exports.deleteAll = (req, res) => {
    db.deleteMany({})
      .then(data => {
        res.send({
          message: `${data.deletedCount} Alerts were deleted successfully!`
        });
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while removing all alerts."
        });
      });
  };