import * as express from 'express';

import CohortCtrl from './controllers/cohort';
import ScheduleCtrl from './controllers/schedule';
import AssignmentCtrl from './controllers/assignment';
import UserCtrl from './controllers/user';

export default function setRoutes(app) {

  const router = express.Router();

  const scheduleCtrl = new ScheduleCtrl();
  const assignmentCtrl = new AssignmentCtrl();
  const cohortCtrl = new CohortCtrl();
  const userCtrl = new UserCtrl();

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

  //Scheduling
  router.route('/schedules').get(scheduleCtrl.getAll);
  router.route('/schedules/count').get(scheduleCtrl.count);
  router.route('/schedule').post(scheduleCtrl.insert);
  router.route('/schedule/:id').get(scheduleCtrl.get);
  router.route('/schedule/:id').delete(scheduleCtrl.delete);

  //assignment handling
  router.route('/assignments').get(assignmentCtrl.getAll);
  router.route('/assignments/count').get(assignmentCtrl.count);
  router.route('/assignment').post(assignmentCtrl.insert);
  router.route('/assignment/:id').get(assignmentCtrl.get);
  router.route('/assignment/:id').delete(assignmentCtrl.delete);

  // Apply the routes to our application with the prefix /api
  app.use('/api', router);

}
