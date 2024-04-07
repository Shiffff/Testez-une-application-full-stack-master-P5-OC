import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { FormComponent } from './form.component';
import { expect } from '@jest/globals';
import { Session } from '../../interfaces/session.interface';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let sessionApiService: SessionApiService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
    },
  };

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
        NoopAnimationsModule,
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService,
      ],
      declarations: [FormComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormComponent);
    sessionApiService = TestBed.inject(SessionApiService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form for new session', () => {
    component.ngOnInit();
    expect(component.onUpdate).toBeFalsy();
    expect(component.sessionForm).toBeTruthy();
    expect(component.sessionForm!.get('name')).toBeTruthy();
    expect(component.sessionForm!.get('date')).toBeTruthy();
    expect(component.sessionForm!.get('teacher_id')).toBeTruthy();
    expect(component.sessionForm!.get('description')).toBeTruthy();
  });

  it('should submit new session form', () => {
    // Simulate form input
    component.sessionForm?.setValue({
      name: 'Test Session',
      date: '2022-05-10',
      teacher_id: '2',
      description: 'Test Description',
    });
    const spyCreate = jest
      .spyOn(sessionApiService, 'create')
      .mockReturnValue(of({} as Session));
    component.submit();
    expect(spyCreate).toHaveBeenCalled();
  });

  it('should submit updated session form', () => {
    component.sessionForm?.setValue({
      name: 'Updated Test Session',
      date: '2022-05-11',
      teacher_id: '3',
      description: 'Updated Test Description',
    });
    component.onUpdate = true;
    const spyUpdate = jest
      .spyOn(sessionApiService, 'update')
      .mockReturnValue(of({} as Session));
    component.submit();
    expect(spyUpdate).toHaveBeenCalled();
  });
});
