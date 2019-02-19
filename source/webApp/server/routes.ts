import * as express from 'express';

import CohortCtrl from './controllers/cohort';
import ScheduleCtrl from './controllers/schedule';
import AssignmentCtrl from './controllers/assignment';
import UserCtrl from './controllers/user';
/*
MIT License

Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
export default function setRoutes(app) {

  const router = express.Router();

  const scheduleCtrl = new ScheduleCtrl();
  const assignmentCtrl = new AssignmentCtrl();
  const cohortCtrl = new CohortCtrl();
  const userCtrl = new UserCtrl();

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
  router.route('/assignments/:name').get(assignmentCtrl.getByName);

  // Apply the routes to our application with the prefix /api
  app.use('/api', router);

}
