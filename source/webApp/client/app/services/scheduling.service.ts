import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Schedule } from '../shared/models/schedule.model';

@Injectable()
export class SchedulingService {

  constructor(private http: HttpClient) { }

  getSchedules(): Observable<Schedule[]> {
    return this.http.get<Schedule[]>('/api/schedules');
  }

  countSchedules(): Observable<number> {
    return this.http.get<number>('/api/schedules/count');
  }

  runScheduling(param : HttpParams) {
    return this.http.post('TODO - specify url for cpp server', param);
  }

  getSchedule(schedule: Schedule): Observable<Schedule> {
    return this.http.get<Schedule>(`/api/schedule/${schedule._id}`);
  }

  deleteSchedule(schedule: Schedule): Observable<any> {
    return this.http.delete(`/api/schedule/${schedule._id}`, { responseType: 'text' });
  }

}
