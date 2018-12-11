import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Cohort } from '../shared/models/cohort.model';

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

@Injectable()
export class CohortService {

  constructor(private http: HttpClient) { }

  getCohorts(): Observable<Cohort[]> {
    return this.http.get<Cohort[]>('/api/cohorts');
  }

  countCohorts(): Observable<number> {
    return this.http.get<number>('/api/cohorts/count');
  }

  addCohort(cohort: Cohort): Observable<Cohort> {
    return this.http.post<Cohort>('/api/cohort', cohort);
  }

  getCohort(cohort: Cohort): Observable<Cohort> {
    return this.http.get<Cohort>(`/api/cohort/${cohort._id}`);
  }

  editCohort(cohort: Cohort): Observable<any> {
    return this.http.put(`/api/cohort/${cohort._id}`, cohort, { responseType: 'text' });
  }

  deleteCohort(cohort: Cohort): Observable<any> {
    return this.http.delete(`/api/cohort/${cohort._id}`, { responseType: 'text' });
  }

}
