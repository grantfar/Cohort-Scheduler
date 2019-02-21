import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Schedule } from '../shared/models/schedule.model';
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
export class SchedulingService {

  constructor(private http: HttpClient) { }

  getSchedules(): Observable<Schedule[]> {
    return this.http.get<Schedule[]>('/api/schedules');
  }

  countSchedules(): Observable<number> {
    return this.http.get<number>('/api/schedules/count');
  }

  runScheduling(csvFile : File, scheduleName: string, reqs: Cohort[]) {
    let data = {
      file: csvFile,
      name: name,
      reqs: reqs
    };
    return this.http.post('localhost:3096/api/start', data);
  }

  getSchedule(schedule: Schedule): Observable<Schedule> {
    return this.http.get<Schedule>(`/api/schedule/${schedule._id}`);
  }

  deleteSchedule(schedule: Schedule): Observable<any> {
    return this.http.delete(`/api/schedule/${schedule._id}`, { responseType: 'text' });
  }

}
