const mongoose = require('mongoose');
const bcrypt = require('bcrypt');

const UserSchema = mongoose.Schema({
    first_name : {
        type : String,
        //required : true
    },
    last_name : {
        type : String,
        //required : true
    },
    username : {
        type : String,
        required : true,
        //unique : true
    },
    email : {
        type : String,
        //unique : true
    },
    num_tel : {
        type : String
    },
    state :{
        type : Number,
        default: 1 
    },
    note :{
        type: Number,
        default: 0
    },
    password: { type: String, required: true },
    confirm_password :{ type: String ,
        // required: true
        },
    signale: {
        type: Number,
        default: 0
    },
    addresses: [
        {
            street: String,
            city: String,
            houseNumber: String
        }
      ],
      addresses_p: {
        type : String
    },
    contact: {
        type : String
    },
    roles: [
        {
          type: mongoose.Schema.Types.ObjectId,
          ref: "Role"
        }
      ]
}
);

/*
UserSchema.set('toJSON', {
    virtuals: true,
    versionKey: false,
    transform: function (doc, ret) {
        delete ret._id;
        delete ret.password;
    }
});
*/

module.exports = mongoose.model("User", UserSchema);





//const User = mongoose.model('User', UserSchema) ;
//export default User ;

/*
{
    "nom": "yousra",
    "prenom": "abid",
    "login": "yohell",
    "email": "yousra.abid@esprit.tn",
    "num_tel": "55171043",
    "passeword": "yousra"
    "adress" : {
        "street": "String",
        "city": "String",
        "houseNumber": "String"
    }
}


*/