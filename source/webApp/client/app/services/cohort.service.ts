import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Cohort } from '../shared/models/cohort.model';

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
