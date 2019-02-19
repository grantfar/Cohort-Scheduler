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
    //this.assignmentId = this.getParam("id");
    this.getAssignments();
  }

  getAssignments() {
    /*this.assignmentService.getAssignments(this.assignmentId).subscribe(
      data => this.assignments = data,
      error => console.log(error),
      () => this.isLoading = false
    );*/
    this.assignments = [{cohort:"MAE2",class:"IEE1020",sect:"100",days:"MWF",startTime:"10:30",endTime:"11:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"MAE2",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"MAE2",class:"MATH1180",sect:"110",days:"MTRF",startTime:"10:00",endTime:"10:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"MAE2",class:"EDMM1420",sect:"542",days:"T",startTime:"12:30",endTime:"15:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"MAE1",class:"IEE1020",sect:"130",days:"MWF",startTime:"09:30",endTime:"10:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"MAE1",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"MAE1",class:"EDMM1420",sect:"558",days:"R",startTime:"12:30",endTime:"15:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"MAE1",class:"MATH1700",sect:"100",days:"MTRF",startTime:"09:00",endTime:"09:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"HON2",class:"IEE1020",sect:"125",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Kohrman Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"HON2",class:"MATH1700",sect:"108",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:25},
      {cohort:"HON2",class:"CHEM1100",sect:"H01",days:"MWF",startTime:"12:00",endTime:"12:50",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:50},
      {cohort:"HON2",class:"EDMM1420",sect:"540",days:"T",startTime:"08:30",endTime:"11:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"UND",class:"IEE1020",sect:"170",days:"MWF",startTime:"14:30",endTime:"15:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"UND",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"UND",class:"MATH1700",sect:"105",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"UND",class:"MATH1180",sect:"125",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"HON1",class:"IEE1020",sect:"105",days:"TR",startTime:"08:30",endTime:"09:45",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"HON1",class:"MATH1220",sect:"100",days:"MTRF",startTime:"08:00",endTime:"08:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"HON1",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"HON1",class:"CHEG1010",sect:"540",days:"W",startTime:"14:30",endTime:"17:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:70},
      {cohort:"HON1",class:"PAPR1000",sect:"500",days:"MW",startTime:"10:00",endTime:"10:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"HON4",class:"IEE1020",sect:"145",days:"TR",startTime:"12:30",endTime:"13:45",campus:"Kohrman Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"HON4",class:"MATH1220",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"HON4",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"HON4",class:"EDMM1420",sect:"562",days:"R",startTime:"15:30",endTime:"18:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"HON4",class:"CS1110",sect:"550",days:"W",startTime:"12:30",endTime:"14:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:26},
      {cohort:"HON3",class:"IEE1020",sect:"135",days:"MWF",startTime:"10:30",endTime:"11:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"HON3",class:"MATH1700",sect:"105",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"HON3",class:"CHEM1100",sect:"110",days:"MWF",startTime:"12:00",endTime:"12:50",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:195},
      {cohort:"HON3",class:"EDMM1420",sect:"510",days:"T",startTime:"18:30",endTime:"20:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:100},
      {cohort:"HON5",class:"MATH1700",sect:"105",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"HON5",class:"CHEM1100",sect:"110",days:"MWF",startTime:"12:00",endTime:"12:50",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:195},
      {cohort:"HON5",class:"EDMM1420",sect:"554",days:"W",startTime:"08:30",endTime:"11:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"PAPR",class:"PAPR1000",sect:"557",days:"W",startTime:"14:30",endTime:"17:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"PAPR",class:"CHEM1100",sect:"H02",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:30},
      {cohort:"PAPR",class:"IEE1020",sect:"165",days:"TR",startTime:"10:00",endTime:"11:15",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"PAPR",class:"MATH1220",sect:"110",days:"MTRF",startTime:"10:00",endTime:"10:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"PAPR",class:"MATH1180",sect:"100",days:"MTRF",startTime:"08:00",endTime:"08:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"CHEG",class:"CHEG1010",sect:"540",days:"W",startTime:"14:30",endTime:"17:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:70},
      {cohort:"CHEG",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"CHEG",class:"IEE1020",sect:"155",days:"TR",startTime:"14:30",endTime:"15:45",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"CHEG",class:"MATH1180",sect:"115",days:"MTRF",startTime:"11:00",endTime:"11:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"CHEG",class:"MATH1220",sect:"125",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Schneider Hall",schedule:"Fall_19_1",seats:25},
      {cohort:"BIOS1",class:"BIOS1600",sect:"100",days:"MW",startTime:"15:30",endTime:"16:45",campus:"Wood Hall",schedule:"Fall_19_1",seats:300},
      {cohort:"BIOS1",class:"ENGL1050",sect:"950",days:"",startTime:"ONLINE",endTime:"null",campus:"Online - Semester Course",schedule:"Fall_19_1",seats:21},
      {cohort:"BIOS1",class:"MATH1220",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"BIOS2",class:"BIOS1600",sect:"100",days:"MW",startTime:"15:30",endTime:"16:45",campus:"Wood Hall",schedule:"Fall_19_1",seats:300},
      {cohort:"BIOS2",class:"ENGL1050",sect:"952",days:"",startTime:"ONLINE",endTime:"null",campus:"Online - Semester Course",schedule:"Fall_19_1",seats:21},
      {cohort:"BIOS2",class:"MATH1180",sect:"100",days:"MTRF",startTime:"08:00",endTime:"08:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"ENV1",class:"GEOS1300",sect:"650",days:"W",startTime:"10:00",endTime:"11:50",campus:"Wood Hall",schedule:"Fall_19_1",seats:19},
      {cohort:"ENV1",class:"ENGL1050",sect:"950",days:"",startTime:"ONLINE",endTime:"null",campus:"Online - Semester Course",schedule:"Fall_19_1",seats:21},
      {cohort:"ENV1",class:"MATH1110",sect:"160",days:"TR",startTime:"15:30",endTime:"16:45",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"ENV1",class:"MATH1180",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"ENV1",class:"MATH1220",sect:"120",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"CCE",class:"IEE1020",sect:"180",days:"TR",startTime:"17:30",endTime:"18:45",campus:"Kohrman Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"CCE",class:"EDMM1420",sect:"543",days:"T",startTime:"15:30",endTime:"18:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"CCE",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"CCE",class:"MATH1700",sect:"108",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:25},
      {cohort:"CCE",class:"MATH1180",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"ECE",class:"ECE2500",sect:"575",days:"W",startTime:"18:30",endTime:"21:10",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"ECE",class:"IEE1020",sect:"115",days:"MWF",startTime:"11:30",endTime:"12:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"ECE",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"ECE",class:"MATH1700",sect:"100",days:"MTRF",startTime:"09:00",endTime:"09:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"ECE",class:"MATH1180",sect:"115",days:"MTRF",startTime:"11:00",endTime:"11:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"IEE",class:"IEE1020",sect:"150",days:"TR",startTime:"11:30",endTime:"12:45",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"IEE",class:"EDMM1420",sect:"567",days:"W",startTime:"18:30",endTime:"21:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"IEE",class:"MATH1220",sect:"110",days:"MTRF",startTime:"10:00",endTime:"10:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"IEE",class:"MATH1700",sect:"100",days:"MTRF",startTime:"09:00",endTime:"09:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"IEE",class:"MATH1180",sect:"100",days:"MTRF",startTime:"08:00",endTime:"08:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"IEE",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"BIOS3",class:"BIOS1600",sect:"100",days:"MW",startTime:"15:30",endTime:"16:45",campus:"Wood Hall",schedule:"Fall_19_1",seats:300},
      {cohort:"BIOS3",class:"ENGL1050",sect:"952",days:"",startTime:"ONLINE",endTime:"null",campus:"Online - Semester Course",schedule:"Fall_19_1",seats:21},
      {cohort:"BIOS3",class:"MATH1110",sect:"190",days:"TR",startTime:"18:30",endTime:"19:45",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"BIOS4",class:"BIOS1600",sect:"100",days:"MW",startTime:"15:30",endTime:"16:45",campus:"Wood Hall",schedule:"Fall_19_1",seats:300},
      {cohort:"BIOS4",class:"ENGL1050",sect:"951",days:"",startTime:"ONLINE",endTime:"null",campus:"Online - Semester Course",schedule:"Fall_19_1",seats:21},
      {cohort:"BIOS4",class:"MATH1220",sect:"120",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"BIOS4",class:"MATH1180",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"BIOS4",class:"MATH1110",sect:"130",days:"TR",startTime:"12:30",endTime:"13:45",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"CS",class:"CS1110",sect:"545",days:"W",startTime:"10:30",endTime:"12:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:26},
      {cohort:"CS",class:"MATH1220",sect:"120",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"CS",class:"MATH1180",sect:"130",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"CS",class:"IEE1020",sect:"175",days:"TR",startTime:"16:00",endTime:"17:15",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"EXP1",class:"ENGR2100",sect:"100",days:"MW",startTime:"12:30",endTime:"13:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:25},
      {cohort:"EXP1",class:"MATH1110",sect:"170",days:"TR",startTime:"17:00",endTime:"18:15",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"EXP1",class:"EDMM1420",sect:"510",days:"T",startTime:"18:30",endTime:"20:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:100},
      {cohort:"EXP2",class:"ENGR2100",sect:"110",days:"TR",startTime:"11:30",endTime:"12:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"EXP2",class:"MATH1110",sect:"155",days:"TR",startTime:"14:00",endTime:"15:15",campus:"Sangren Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"EXP2",class:"EDMM1420",sect:"564",days:"F",startTime:"08:30",endTime:"11:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"EXP3",class:"ENGR2100",sect:"120",days:"MW",startTime:"12:30",endTime:"13:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"EXP3",class:"MATH1110",sect:"130",days:"TR",startTime:"12:30",endTime:"13:45",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"EXP3",class:"EDMM1420",sect:"548",days:"F",startTime:"15:30",endTime:"18:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"EXP4",class:"ENGR2100",sect:"125",days:"TR",startTime:"12:30",endTime:"13:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"EXP4",class:"MATH1110",sect:"125",days:"MWF",startTime:"12:00",endTime:"12:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:40},
      {cohort:"EXP5",class:"ENGR2100",sect:"115",days:"MW",startTime:"11:30",endTime:"12:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:30},
      {cohort:"EXP5",class:"MATH1110",sect:"160",days:"TR",startTime:"15:30",endTime:"16:45",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"AERO",class:"CHEM1100",sect:"103",days:"TR",startTime:"11:00",endTime:"12:15",campus:"Chemistry Bldg",schedule:"Fall_19_1",seats:235},
      {cohort:"AERO",class:"IEE1020",sect:"110",days:"TR",startTime:"10:00",endTime:"11:15",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:20},
      {cohort:"AERO",class:"EDMM1420",sect:"546",days:"W",startTime:"12:30",endTime:"15:20",campus:"Elson S. Floyd Hall",schedule:"Fall_19_1",seats:24},
      {cohort:"AERO",class:"MATH1700",sect:"105",days:"MTRF",startTime:"14:00",endTime:"14:50",campus:"Rood Hall",schedule:"Fall_19_1",seats:35},
      {cohort:"AERO",class:"MATH1180",sect:"125",days:"MTRF",startTime:"13:00",endTime:"13:50",campus:"Sangren Hall",schedule:"Fall_19_1",seats:35}]
  }

  getParam(param:string){
    let qs = location.search;
    let urlParam = new URLSearchParams(qs);
    return urlParam.get(param);
  }
}
