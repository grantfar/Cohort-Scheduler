import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import { SchedulingService } from '../services/scheduling.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Schedule } from '../shared/models/schedule.model';
import { CohortService } from '../services/cohort.service';
import { Cohort } from '../shared/models/cohort.model';
import schedule from '../../../server/models/schedule';

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
  reqs: Cohort[] = [];
  scheduleName:string;

  runScheduleForm: FormGroup;
  name = new FormControl('', Validators.required);
  count = new FormControl('', Validators.required);
  file:File = null;

  constructor(private schedulingService: SchedulingService,
              private cohortService: CohortService,
              private formBuilder: FormBuilder,
              public toast: ToastComponent) { }

  ngOnInit() {
    this.getSchedules();
    this.getCohorts();
    this.runScheduleForm = this.formBuilder.group({
      name: this.name
    });
  }

  getCohorts() {
    this.cohortService.getCohorts().subscribe(
      data => this.reqs = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  getSchedules() {
      this.schedulingService.getSchedules().subscribe(
      data => this.schedules = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  runSchedule() {
    let fileForm:FormData = new FormData();
    fileForm.append('file', this.file);
    this.schedulingService.sendFile(fileForm).subscribe(
      res => {
        this.toast.setMessage('File uploaded successfully.', 'success');
        this.createSchedule();
      },
      error => {
        console.log(error);
          this.toast.setMessage('File upload failed.', 'error');
      }
    );
  }
  
  handleFileUpload(files: FileList) {
    this.file = files.item(0);
}

  createSchedule(){
    let newSch = null;
    let name:string = this.scheduleName;
    name.replace(/\s+/g, '_');
    let sched:Schedule = new Schedule()
    sched.name = name;
    sched.date = new Date().toString();
    this.schedulingService.addSchedule(sched).subscribe(
      (res:any) =>{
        newSch = {name:res.name, date:res.date, _id:res._id};
        this.initScheduling(name, newSch); 
      }
    );
  }

  initScheduling(name:string, newSch:Schedule){
    console.log("startInit: "+newSch)
    let params = {
      requirements: this.reformatCohorts(),
      name: name,
      date: new Date().toString()
    }
    
    this.schedulingService.runScheduling(params).subscribe(data =>{
      if(data=="0"){
        this.toast.setMessage('Failed to initiate scheduling', 'error');
          this.schedulingService.deleteSchedule(newSch);
        
      }else if(data=="-1"){
        this.toast.setMessage('Some required classes are not in the excel file. Failed to start scheduling.', 'error');
        console.log("deleting");
        this.schedulingService.deleteSchedule(newSch).subscribe(data => console.log(data));
        
      }
      else{
        this.toast.setMessage('Scheduling initiated', 'success');
      }
    });
  }

  reformatCohorts():any[]{
    let reformatted:any[] = [];
    this.reqs.forEach(element=>{
      reformatted.push({
        cohort: element.cohort,
        course: element.class,
        sectionsAllowed: element.sections,
        seatsNeeded: element.required
      });
    });
    return reformatted;
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
