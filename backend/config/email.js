const hbs = require('nodemailer-express-handlebars');
const nodemailer = require('nodemailer');
const path = require('path');

const email=process.env.EMAIL;
const password=process.env.PASSWORD;

const sendemail = function(username, mycode,email){
    console.log('username sent: ' + username);
    console.log('mycode sent: ' + mycode);
    console.log('email sent: ' + email);


// initialize nodemailer
var transporter = nodemailer.createTransport(
    {
        service: 'gmail',
        auth:{
            user: 'alerto.alto@gmail.com',
            pass: '15erfd54sf5'
        }
    }
);

// point to the template folder


// use a template file with nodemailer



var mailOptions = {
    from: '"Alerto" <alerto.alto@gmail.com>', // sender address
    to: email, // list of receivers
    subject: 'Confirmation code',
    html: '<head><title>Welcome</title></head><body><h2>Hello '+username+
    ' !</h2><p>We\'re glad to have you on board at Alerto</p><p>Your confirmation code is : '+mycode+'</p></body>'
};

// trigger the sending of the E-mail
transporter.sendMail(mailOptions, function(error, info){
    if(error){
        return console.log(error);
    }
    console.log('Message sent: ' + info.response);
});

}

const randomString = function(length){
    var result           = '';
    var characters       = '01234567891234567890';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
       result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

 
module.exports ={
    sendemail,randomString
}