// require the Twilio module and MyRequestClient
const twilio = require('twilio');
const MyRequestClient = require('./MyRequestClient');
const accountSid = 'AC721aad8251f5940bf390a62e780fc1ed'; 
const authToken = 'a29337c104b6ba3c0cc6d74862207081'; 


const phoneVer = function(mycode,phone){
 
const client = twilio(accountSid, authToken, {
  // Custom HTTP Client with a one minute timeout
  httpClient: new MyRequestClient(60000),
});

client.messages
  .create({
    body: 'Your Alerto verification code is : '+mycode,  
    messagingServiceSid: 'MG46e1b9f18078bf4b1ce2d938ca5add94',      
    to: phone
  })
  .then((message) => console.log(`Message SID ${message.sid}`))
  .catch((error) => console.error(error));

/*
client.messages 
      .create({ 
         body: 'Your Alerto verification code is : '+mycode,  
         messagingServiceSid: 'MG46e1b9f18078bf4b1ce2d938ca5add94',      
         to: phone
       }) 
      .then(message => console.log(message.sid)) 
      .done();
    }
*/
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
        phoneVer,randomString
    }