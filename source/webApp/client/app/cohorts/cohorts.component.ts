import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { CohortService } from '../services/cohort.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Cohort } from '../shared/models/cohort.model';

@Component({
  selector: 'app-cohorts',
  templateUrl: './cohorts.component.html',
  styleUrls: ['./cohorts.component.scss']
})
export class CohortsComponent implements OnInit {

  cohort = new Cohort();
  cohorts: Cohort[] = [];
  isLoading = true;
  isEditing = false;

  addCohortForm: FormGroup;
  name = new FormControl('', Validators.required);
  age = new FormControl('', Validators.required);
  weight = new FormControl('', Validators.required);

  constructor(private cohortService: CohortService,
              private formBuilder: FormBuilder,
              public toast: ToastComponent) { }

  ngOnInit() {
    this.getCohorts();
    this.addCohortForm = this.formBuilder.group({
      name: this.name,
      age: this.age,
      weight: this.weight
    });
  }

  getCohorts() {
    this.cohortService.getCohorts().subscribe(
      data => this.cohorts = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  addCohort() {
    this.cohortService.addCohort(this.addCohortForm.value).subscribe(
      res => {
        this.cohorts.push(res);
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
