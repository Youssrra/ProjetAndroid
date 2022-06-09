const express = require('express');
const alert = require('../controller/alert.js');
const router = express.Router();

//const getAlerts  = require('../controller/alert.js');

/*router.get('/get', getAlerts);
router.get('/', add);
router*/


router.get("/getAll", alert.getAlerts);
router.get("/getbyid/:id", alert.getAlertbyId);
router.get("/find", alert.findAlert);
router.post("/create", alert.createAlert);
router.put("/update/:id", alert.updateAlert);
router.delete("/delete/:id",alert.deleteAlert);
router.delete("/deleteall", alert.deleteAll);

module.exports = router;


/*
router.get('/',(req,res) => {
    res.send('We are on alert');
});


router.post('/' , async (req,res) => {
    const alert = new Alert({
        title: req.body.title,
        description: req.body.description,
        contenu: req.body.contenu
    });

    try{
    const savedAlert = await alert.save();
    res.json(savedAlert);
}catch(err){
    res.json({ message : err });
}
   
});
*/
