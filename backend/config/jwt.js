const expressJwt = require('express-jwt');
//const config = require('config.json');
const userService = require('../service/user.service');

module.exports = jwt;

function jwt() {
    const secret = "48DBCA8BA46C8AA2D1F7A51869A6017CDC24EED8E73DF9AB3B73F8AE561E4D34";
    return expressJwt({ secret, algorithms: ['HS256'], isRevoked }).unless({
        path: [
            // public routes that don't require authentication
            '/users/authenticate',
            '/users/register'
        ]
    });
}

async function isRevoked(req, payload, done) {
    const user = await userService.getById(payload.sub);

    // revoke token if user no longer exists
    if (!user) {
        return done(null, true);
    }

    done();
};