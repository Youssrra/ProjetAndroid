const express = require('express');
const router = express.Router();
const comment = require('../controller/comment.js');




router.get("/getAll", comment.getComments);
router.get("/getbyid/:id", comment.getCommentbyId);
router.get("/find", comment.findComment);
router.post("/create", comment.createComment);
router.put("/update/:id", comment.updateComment);
router.delete("/delete/:id", comment.deleteComment);
router.delete("/deleteall", comment.deleteAll);

module.exports = router;
/*
{

    "contenu": "ceci est un commentaire",
    "postedBy": "61953701e6856c11a3867d79",
    "post": "619060523f893e40b7ed229e"
   
}

*/