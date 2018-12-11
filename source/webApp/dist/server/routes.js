"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var express = require("express");
var cohort_1 = require("./controllers/cohort");
var schedule_1 = require("./controllers/schedule");
var assignment_1 = require("./controllers/assignment");
var user_1 = require("./controllers/user");
function setRoutes(app) {
    var router = express.Router();
    var scheduleCtrl = new schedule_1.default();
    var assignmentCtrl = new assignment_1.default();
    var cohortCtrl = new cohort_1.default();
    var userCtrl = new user_1.default();
    // Cohorts localhost:3000/cohorts
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
    //Scheduling
    router.route('/schedules').get(scheduleCtrl.getAll);
    router.route('/schedules/count').get(scheduleCtrl.count);
    router.route('/schedule').post(scheduleCtrl.insert);
    router.route('/schedule/:id').get(scheduleCtrl.get);
    router.route('/schedule/:id').delete(scheduleCtrl.delete);
    //assignment handling POST:localhost:3000/assignment
    router.route('/assignments').get(assignmentCtrl.getAll);
    router.route('/assignments/count').get(assignmentCtrl.count);
    router.route('/assignment').post(assignmentCtrl.insert);
    router.route('/assignment/:id').get(assignmentCtrl.get);
    router.route('/assignment/:id').delete(assignmentCtrl.delete);
    // Apply the routes to our application with the prefix /api
    app.use('/api', router);
}
exports.default = setRoutes;
//# sourceMappingURL=routes.js.map