import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { CohortService } from '../services/cohort.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Cohort } from '../shared/models/cohort.model'; 
import { ScheduleViewService } from '../services/scheduleView.service';

@Component({
  selector: 'app-cohorts',
  templateUrl: './scheduleView.component.html',
  styleUrls: ['./scheduleView.service.scss']
})
export class ScheduleViewComponent implements OnInit {

    cohort = new Cohort();
    cohorts: Cohort[] = [];
    jsonString: string = "";
  
    

  constructor(private scheduleView: ScheduleViewService,
              ) { }

  ngOnInit() {
    this.getCohorts();
    this.addCohortForm = this.formBuilder.group({
      cohort: this.cohortName,
      class: this.class,
    });
  }

  getCohorts() {
    this.cohortService.getCohorts().subscribe(
      data => this.cohorts = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

}
