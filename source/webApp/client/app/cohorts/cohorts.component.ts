import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { CohortService } from '../services/cohort.service';
import { ToastComponent } from '../shared/toast/toast.component';
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


@Component({
  selector: 'app-cohorts',
  templateUrl: './cohorts.component.html',
  styleUrls: ['./cohorts.component.scss']
})
export class CohortsComponent implements OnInit {

  cohort = new Cohort();
  cohorts:Cohort[] = [];
  isLoading = true;
  isEditing = false;

  cohortNames:string[] = [];

  addCohortForm: FormGroup;
  cohortName = new FormControl('', Validators.required);
  class = new FormControl('', Validators.required);
  required = new FormControl('', Validators.required);
  sections = new FormControl('');

  constructor(private cohortService: CohortService,
              private formBuilder: FormBuilder,
              public toast: ToastComponent) { }

  ngOnInit() {
    this.getCohorts();
    this.addCohortForm = this.formBuilder.group({
      cohort: this.cohortName,
      class: this.class,
      required: this.required,
      sections: this.sections
    });
  }

  getCohorts() {
    this.cohortService.getCohorts().subscribe(
      data => {
        this.cohorts = data;
        this.cohorts.forEach(element => {
          if(!this.cohortNames.includes(element.cohort)){
            this.cohortNames.push(element.cohort);
          }
        });
      },
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  addCohort() {
    if(this.addCohortForm['sections'].value == null){
      this.addCohortForm['sections'].value ="";
    }
    this.cohortService.addCohort(this.addCohortForm.value).subscribe(
      res => {
        this.cohorts.push(res);
        this.cohortNames = [];
        this.cohorts.forEach(element => {
          if(!this.cohortNames.includes(element.cohort)){
            this.cohortNames.push(element.cohort);
          }
        });
        this.addCohortForm.reset();
        this.toast.setMessage('item added successfully.', 'success');
      },
      error => console.log(error)
    );
  }

  enableEditing(cohort: Cohort) {
    this.isEditing = true;
    this.cohort = cohort;
  }

  cancelEditing() {
    this.isEditing = false;
    this.cohort = new Cohort();
    this.toast.setMessage('item editing cancelled.', 'warning');
    // reload the cohorts to reset the editing
    this.getCohorts();
  }

  editCohort(cohort: Cohort) {
    this.cohortService.editCohort(cohort).subscribe(
      () => {
        this.isEditing = false;
        this.cohort = cohort;
        this.toast.setMessage('item edited successfully.', 'success');
      },
      error => console.log(error)
    );
  }

  deleteCohort(cohort: Cohort) {
    if (window.confirm('Are you sure you want to permanently delete this item?')) {
      this.cohortService.deleteCohort(cohort).subscribe(
        () => {
          const pos = this.cohorts.map(elem => elem._id).indexOf(cohort._id);
          this.cohorts.splice(pos, 1);
          this.toast.setMessage('item deleted successfully.', 'success');
        },
        error => console.log(error)
      );
    }
  }

}
