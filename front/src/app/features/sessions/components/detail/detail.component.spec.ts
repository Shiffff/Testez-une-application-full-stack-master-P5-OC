import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { of } from 'rxjs';
import { Session } from '../../interfaces/session.interface';
import { Teacher } from '../../../../interfaces/teacher.interface';
import { SessionService } from '../../../../services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

import { DetailComponent } from './detail.component';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let sessionApiServiceMock: SessionApiService;
  let teacherServiceMock: TeacherService;
  let sessionServiceMock: SessionService;

  const mockSession: Session = {
    id: 1,
    name: 'string',
    description: 'string',
    date: new Date(),
    teacher_id: 1,
    users: [],
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockTeacher: Teacher = {
    id: 1,
    lastName: 'string',
    firstName: 'string',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  beforeEach(async () => {
    sessionApiServiceMock = {
      detail: jest.fn().mockReturnValue(of(mockSession)),
      participate: jest.fn().mockReturnValue(of({})),
      unParticipate: jest.fn().mockReturnValue(of({})),
      delete: jest.fn().mockReturnValue(of({})),
    } as unknown as SessionApiService;

    teacherServiceMock = {
      detail: jest.fn().mockReturnValue(of(mockTeacher)),
    } as unknown as TeacherService;

    sessionServiceMock = {
      sessionInformation: { admin: true, id: 1 },
    } as unknown as SessionService;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionApiService, useValue: sessionApiServiceMock },
        { provide: TeacherService, useValue: teacherServiceMock },
        { provide: SessionService, useValue: mockSessionService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    component.sessionId = '1';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch session details', () => {
    component.fetchSession();
    expect(component.session).toEqual(mockSession);
    expect(component.isParticipate).toBe(false); // or true, depending on the session user participation
    expect(component.teacher).toEqual(mockTeacher);
  });

  it('should delete session', () => {
    component.delete();
    expect(sessionApiServiceMock.delete).toHaveBeenCalledWith('1');
  });

  it('should navigate back', () => {
    const historySpy = jest.spyOn(window.history, 'back');

    component.back();
    expect(historySpy).toHaveBeenCalled();
  });
});
