"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var express = require("express");
var cohort_1 = require("./controllers/cohort");
var user_1 = require("./controllers/user");
function setRoutes(app) {
    var router = express.Router();
    var cohortCtrl = new cohort_1.default();
    var userCtrl = new user_1.default();
    // Cohorts
    router.route('/cohorts').get(cohortCtrl.getAll);
    router.route('/cohorts/count').get(cohortCtrl.count);
    router.route('/cohort').post(cohortCtrl.insert);
    router.route('/cohort/:id').get(cohortCtrl.get);
    router.route('/cohort/:id').put(cohortCtrl.update);
    router.route('/cohort/:id').delete(cohortCtrl.delete);
    // Users
    router.route('/login').post(userCtrl.login);
    router.route('/users').get(userCtrl.getAll);
    router.route('/users/count').get(userCtrl.count);
    router.route('/user').post(userCtrl.insert);
    router.route('/user/:id').get(userCtrl.get);
    router.route('/user/:id').put(userCtrl.update);
    router.route('/user/:id').delete(userCtrl.delete);
    // Apply the routes to our application with the prefix /api
    app.use('/api', router);
}
exports.default = setRoutes;
//# sourceMappingURL=routes.js.map