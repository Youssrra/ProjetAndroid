const mongoose = require('mongoose');

const AlertSchema = mongoose.Schema({
    title : String,
    description : String,
    contenu : String,
    date_p : {
        type : Date,
        default: Date.now
    },
    tags : [String],
    file : String,
    like : {
        type : Number,
        default: 0
    },
    signale:{
        type: Number,
        default: 0
    },
    postedBy: {
        type: mongoose.Schema.Types.ObjectId, ref: 'User'
    },
});

//const Alert = mongoose.model('Alert', AlertSchema) ;


module.exports = mongoose.model('Alert', AlertSchema) ;
