import * as chai from 'chai';
import { describe, it } from 'mocha';

process.env.NODE_ENV = 'test';
import { app } from '../app';
import Cohort from '../models/cohort';

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


chai.use(require('chai-http')).should();

describe('Cohorts', () => {

  beforeEach(done => {
    Cohort.remove({}, err => {
      done();
    });
  });

  describe('Backend tests for cohorts', () => {

    it('should get all the cohorts', done => {
      chai.request(app)
        .get('/api/cohorts')
        .end((err, res) => {
          res.should.have.status(200);
          res.body.should.be.a('array');
          res.body.length.should.be.eql(0);
          done();
        });
    });

    it('should get cohorts count', done => {
      chai.request(app)
        .get('/api/cohorts/count')
        .end((err, res) => {
          res.should.have.status(200);
          res.body.should.be.a('number');
          res.body.should.be.eql(0);
          done();
        });
    });

    it('should create new cohort', done => {
      const cohort = new Cohort({ cohort: 'Fluffy', class: 'cs100', required: 2, sections:'H' });
      chai.request(app)
        .post('/api/cohort')
        .send(cohort)
        .end((err, res) => {
          res.should.have.status(201);
          res.body.should.be.a('object');
          res.body.should.have.a.property('cohort');
          res.body.should.have.a.property('class');
          res.body.should.have.a.property('required');
          res.body.should.have.a.property('sections');
          done();
        });
    });

    it('should get a cohort by its id', done => {
      const cohort = new Cohort({ cohort: 'Fluffy', class: 'cs100', required: 2, sections:'H' });
      cohort.save((error, newCohort) => {
        chai.request(app)
          .get(`/api/cohort/${newCohort.id}`)
          .end((err, res) => {
            res.should.have.status(200);
            res.body.should.be.a('object');
            res.body.should.have.a.property('cohort');
            res.body.should.have.a.property('class');
            res.body.should.have.a.property('required');
            res.body.should.have.a.property('sections');
            res.body.should.have.property('_id').eql(newCohort.id);
            done();
          });
      });
    });

    it('should update a cohort by its id', done => {
      const cohort = new Cohort({ cohort: 'Fluffy', class: 'cs100', required: 2, sections:'H' });
      cohort.save((error, newCohort) => {
        chai.request(app)
          .put(`/api/cohort/${newCohort.id}`)
          .send({ weight: 5 })
          .end((err, res) => {
            res.should.have.status(200);
            done();
          });
      });
    });

    it('should delete a cohort by its id', done => {
      const cohort = new Cohort({ cohort: 'Fluffy', class: 'cs100', required: 2, sections:'H' });
      cohort.save((error, newCohort) => {
        chai.request(app)
          .del(`/api/cohort/${newCohort.id}`)
          .end((err, res) => {
            res.should.have.status(200);
            done();
          });
      });
    });
  });

});


