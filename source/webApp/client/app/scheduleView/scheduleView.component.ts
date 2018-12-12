import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { CohortService } from '../services/cohort.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Cohort } from '../shared/models/cohort.model'; 
import { ScheduleViewService } from '../services/scheduleView.service';
import { Schedule } from '../shared/models/schedule.model';


@Component({
  selector: 'app-cohorts',
  templateUrl: './scheduleView.component.html',
  styleUrls: ['./scheduleView.component.scss']
}) 

export class ScheduleViewComponent implements OnInit {

    cohort = new Cohort();
    cohorts: Cohort[] = []; 
    schedules: Schedule[] = [];
    jsonString: string = "";
  

    

  constructor(private scheduleView: ScheduleViewService,
              ) { }

  ngOnInit() {
   this.getCohorts(); 
  


  }

  getCohorts() {
  
      
  
  }

}
