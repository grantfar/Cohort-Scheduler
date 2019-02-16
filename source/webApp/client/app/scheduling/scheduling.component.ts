import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import { SchedulingService } from '../services/scheduling.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Schedule } from '../shared/models/schedule.model';

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


@Component({
  selector: 'app-scheduling',
  templateUrl: './scheduling.component.html',
  styleUrls: ['./scheduling.component.scss']
})
export class SchedulingComponent implements OnInit {

  schedule = new Schedule();
  schedules: Schedule[] = [];
  isLoading = true;
  isEditing = false;

  runScheduleForm: FormGroup;
  name = new FormControl('', Validators.required);
  count = new FormControl('', Validators.required);
  file:File = null;

  constructor(private schedulingService: SchedulingService,
              private formBuilder: FormBuilder,
              public toast: ToastComponent) { }

  ngOnInit() {
    this.getSchedules();
    this.runScheduleForm = this.formBuilder.group({
      name: this.name,
      count: this.count
    });
  }

  getSchedules() {
      this.schedulingService.getSchedules().subscribe(
      data => this.schedules = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  runSchedule() {
    let name:string = this.runScheduleForm.controls['name'].value;
    name.replace(/\s+/g, '_');
    this.schedulingService.runScheduling(this.file, name, this.runScheduleForm.controls['count'].value).subscribe(
      res => {
        this.toast.setMessage('Scheduling initiated, check back in a few minutes.', 'success');
      },
      error => console.log(error)
    );
  }

  view(schedule: Schedule) {
    //redirect will happen here
    window.location.href = "/assignments?sch="+schedule.name;
  }

  deleteSchedule(schedule: Schedule) {
    if (window.confirm('Are you sure you want to permanently delete this item?')) {
      this.schedulingService.deleteSchedule(schedule).subscribe(
        () => {
          const pos = this.schedules.map(elem => elem._id).indexOf(schedule._id);
          this.schedules.splice(pos, 1);
          this.toast.setMessage('item deleted successfully.', 'success');
        },
        error => console.log(error)
      );
    }
  }

}
