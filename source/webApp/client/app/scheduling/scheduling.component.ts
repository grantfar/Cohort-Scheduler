import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import { SchedulingService } from '../services/scheduling.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Schedule } from '../shared/models/schedule.model';

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
    this.schedulingService.runScheduling(this.file, this.runScheduleForm.controls['name'].value, this.runScheduleForm.controls['count'].value).subscribe(
      res => {
        this.toast.setMessage('Scheduling initiated, check back in a few minutes.', 'success');
      },
      error => console.log(error)
    );
  }

  view(schedule: Schedule) {
    //redirect will happen here
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
