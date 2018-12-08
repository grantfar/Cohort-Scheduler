import * as express from 'express';

import CohortCtrl from './controllers/cohort';
import UserCtrl from './controllers/user';

export default function setRoutes(app) {

  const router = express.Router();

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

  // Apply the routes to our application with the prefix /api
  app.use('/api', router);

}
