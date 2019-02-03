import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import { AssignmentService } from '../services/assignment.service';
import { ToastComponent } from '../shared/toast/toast.component';
import { Assignment } from '../shared/models/assignment.model';
import { URLSearchParams } from 'url';

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
  selector: 'app-assignment',
  templateUrl: './assignment.component.html',
  styleUrls: ['./scheduling.component.scss']
})
export class AssignmentComponent implements OnInit {

  assignments: Assignment[] = [];
  isLoading = true;
  isEditing = false;
  assignmentId;
  constructor(private assignmentService: AssignmentService,
              private formBuilder: FormBuilder,
              public toast: ToastComponent) { }

  ngOnInit() {
    this.assignmentId = this.getParam("id");
    this.getAssignments();
  }

  getAssignments() {
    /*let id = "Fall 18 v1";
    this.assignmentService.getAssignments(id).subscribe(
      data => this.assignments = data,
      error => console.log(error),
      () => this.isLoading = false
    );*/

    this.assignments = [{
      cohort: "BIOS1",
      class: "BIO1600",
      sect: "105",
      days: "TR",
      time: "10:00 - 11:40",
      campus: "M",
      ct: "18"
    },
    {
      cohort: "BIOS1",
      class: "MATH1110",
      sect: "F09",
      days: "MWF",
      time: "9:00 - 9:50",
      campus: "P",
      ct: "18"
    },
    {
      cohort: "BIOS1",
      class: "ENGL1050",
      sect: "F03",
      days: "MTRF",
      time: "12:00 - 12:50",
      campus: "M",
      ct:"18"
    },
    {
      cohort: "ENV1",
      class: "GEOS1300",
      sect: "100",
      days: "MTRF",
      time: "15:00 - 15:50",
      campus: "M",
      ct:"18"
    },
    {
      cohort: "ENV1",
      class: "ENGL1050",
      sect: "110",
      days:"MW",
      time: "10:00 - 11:40",
      campus: "M",
      ct: "18"
    },
    {
      cohort:"ENV1",
      class: "MATH1110",
      sect:"150",
      days: "TR",
      time: "9:00 - 10:40",
      campus:"P",
      ct: "6"
    },
    {
      cohort:"ENV1",
      class: "MATH1180",
      sect:"120",
      days: "TR",
      time: "9:00 - 10:40",
      campus:"P",
      ct: "6"
    },
    {
      cohort:"ENV1",
      class: "MATH1220",
      sect:"108",
      days: "MTRF",
      time: "14:00 - 14:50",
      campus:"M",
      ct: "6"
    }]
  }

  getParam(param:string){
    let qs = location.search;
    let urlParam = new URLSearchParams(qs);
    return urlParam.get(param);
  }
}
