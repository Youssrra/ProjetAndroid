const db = require('../models/Message.js')


// GET ALL
exports.getMessages = (req,res,next) => {
    db.find({}).then(function(message){
        res.send(message);
    }).catch(next);
    
};

// CREATE
exports.createMessage = (req,res,next) => {

    if (!req.body) {
        res.status(400).send({ message: "Content can not be empty!" });
        return;
      }

    db.create(req.body).then(function(message){
        res.send(message);
    }).catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while creating the message."
        });
      });

}

// UPDATE
exports.updateMessage =(req,res,next) => {
    
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
              message: `Cannot update message with id=${id}. Maybe this message dosn't exist!`
            });
          } else res.send({ message: "Message was updated successfully." });
        })
        .catch(err => {
          res.status(500).send({
            message: "Error updating Message with id=" + id
          });
        });
    };
    
// DELETE
exports.deleteMessage = (req,res,next) => {
    db.findOneAndDelete({_id: req.params.id}).then(function(message){
        res.send(message);
    }).catch(err => {
        res
          .status(500)
          .send({ message: "Error while deleting message with id=" + req.params.id });
      });
};

// GET BY ID
exports.getMessagebyId = (req, res) => {
    const id = req.params.id;
  
    db.findById(id)
      .then(data => {
        if (!data)
          res.status(404).send({ message: "Message not found with id " + id });
        else res.send(data);
      })
      .catch(err => {
        res
          .status(500)
          .send({ message: "Error retrieving message with id=" + id });
      });
  };

  // FIND BY
  exports.findMessage = (req, res) => {
    db.find({ like: 1 })
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving messages."
        });
      });
  };

  // DELETE ALL
  exports.deleteAll = (req, res) => {
    db.deleteMany({})
      .then(data => {
        res.send({
          message: `${data.deletedCount} Messages were deleted successfully!`
        });
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while removing all Messages."
        });
      });
  };