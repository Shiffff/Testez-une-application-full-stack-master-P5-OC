import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from 'src/app/services/session.service';
import { expect } from '@jest/globals';

import { MeComponent } from './me.component';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userServiceMock: UserService;
  let sessionServiceMock: SessionService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  const mockUser: User = {
    id: 1,
    email: 'test@test.fr',
    lastName: 'string',
    firstName: 'string',
    admin: true,
    password: 'string',
    createdAt: new Date(),
    updatedAt: new Date(),
  };

  beforeEach(async () => {
    userServiceMock = {
      getById: jest.fn().mockReturnValue(of(mockUser)),
      delete: jest.fn().mockReturnValue(of(undefined)),
    } as unknown as UserService;

    sessionServiceMock = {
      sessionInformation: { id: 1 },
      logOut: jest.fn(),
    } as unknown as SessionService;

    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
      ],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: SessionService, useValue: sessionServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch user data on ngOnInit', () => {
    component.ngOnInit();

    expect(userServiceMock.getById).toHaveBeenCalledWith('1');
    expect(component.user).toEqual(mockUser);
  });
  it('should navigate back in history', () => {
    const historySpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(historySpy).toHaveBeenCalled();
  });

  it('should delete user account and logout', () => {
    component.delete();

    expect(userServiceMock.delete).toHaveBeenCalledWith('1');
  });
});
