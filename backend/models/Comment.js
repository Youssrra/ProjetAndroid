const mongoose = require('mongoose');

const CommentSchema = mongoose.Schema({
    
    contenu : String,
    date_p : {
        type : Date,
        default: Date.now
    },
    like : {
        type : Number,
        default: 0
    },
    postedBy: {
        type: mongoose.Schema.Types.ObjectId, ref: 'User'
    },
    post: {
        type: mongoose.Schema.Types.ObjectId, ref: 'Alert'
    }

});

module.exports = mongoose.model('Comment', CommentSchema) ;

//const Comment = mongoose.model('Comment', CommentSchema) ;
//export default Comment ;
