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
  styleUrls: ['./assignment.component.scss']
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
    this.assignmentService.getAssignments(this.assignmentId).subscribe(
      data => this.assignments = data,
      error => console.log(error),
      () => this.isLoading = false
    );
  }

  getParam(param:string){
    let qs = location.search;
    let urlParam = new URLSearchParams(qs);
    return urlParam.get(param);
  }
}
