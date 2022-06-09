const express = require('express');
const router = express.Router();
const message = require('../controller/message.js');

router.get("/getAll", message.getMessages);
router.get("/getbyid/:id", message.getMessagebyId);
router.get("/find", message.findMessage);
router.post("/create", message.createMessage);
router.put("/update/:id", message.updateMessage);
router.delete("/delete/:id", message.deleteMessage);
router.delete("/deleteall", message.deleteAll);

module.exports = router;