import { TestBed, async } from '@angular/core/testing';
import { SchedulingComponent } from './scheduling.component';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {HttpParams} from '@angular/common/http';
import { SchedulingService } from '../services/scheduling.service';
import { ToastComponent } from '../shared/toast/toast.component';

describe('Component: Scheduling', () => {
  it('should create an instance', () => {
  	let serv: SchedulingService;
  	let form: FormBuilder;
  	let toast: ToastComponent
    let component = new SchedulingComponent(serv, form, toast);
    expect(component).toBeTruthy();
  });
});
