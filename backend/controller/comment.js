const db = require('../models/Comment.js')

// GET ALL
exports.getComments = (req,res,next) => {
    db.find({}).then(function(comment){
        res.send(comment);
    }).catch(next);
    
};

// CREATE
exports.createComment = (req,res,next) => {

    if (!req.body) {
        res.status(400).send({ message: "Content can not be empty!" });
        return;
      }

    db.create(req.body).then(function(comment){
        res.send(comment);
    }).catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while creating the comment."
        });
      });

}

// UPDATE
exports.updateComment =(req,res,next) => {
    
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
              message: `Cannot update comment with id=${id}. Maybe this comment dosn't exist!`
            });
          } else res.send({ message: "Comment was updated successfully." });
        })
        .catch(err => {
          res.status(500).send({
            message: "Error updating Comment with id=" + id
          });
        });
    };
    
// DELETE
exports.deleteComment = (req,res,next) => {
    db.findOneAndDelete({_id: req.params.id}).then(function(comment){
        res.send(comment);
    }).catch(err => {
        res
          .status(500)
          .send({ message: "Error while deleting comment with id=" + req.params.id });
      });
};

// GET BY ID
exports.getCommentbyId = (req, res) => {
    const id = req.params.id;
  
    db.findById(id)
      .then(data => {
        if (!data)
          res.status(404).send({ message: "Comment not found with id " + id });
        else res.send(data);
      })
      .catch(err => {
        res
          .status(500)
          .send({ message: "Error retrieving comment with id=" + id });
      });
  };

  // FIND BY
  exports.findComment = (req, res) => {
    db.find({ like: 1 })
      .then(data => {
        res.send(data);
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving comments."
        });
      });
  };

  // DELETE ALL
  exports.deleteAll = (req, res) => {
    db.deleteMany({})
      .then(data => {
        res.send({
          message: `${data.deletedCount} Comments were deleted successfully!`
        });
      })
      .catch(err => {
        res.status(500).send({
          message:
            err.message || "Some error occurred while removing all Comments."
        });
      });
  };