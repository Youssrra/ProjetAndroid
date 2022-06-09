const mongoose = require('mongoose');

const MessageSchema = mongoose.Schema({
    
    contenu : String,
    date_envoi : {
        type : Date,
        default: Date.now
    },
    signalement : {
        type : Number,
        default: 0
    },
    sentBy: {
        type: mongoose.Schema.Types.ObjectId, ref: 'User'
    },
    sentTo: {
        type: mongoose.Schema.Types.ObjectId, ref: 'User'
    }
    
});

//const Message = mongoose.model('Message', MessageSchema) ;
//export default Message ;

module.exports = mongoose.model('Message', MessageSchema) ;
