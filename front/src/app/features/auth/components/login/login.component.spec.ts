import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { of, throwError } from 'rxjs';

const mockSessionInformation = {
  token: 'string',
  type: 'string',
  id: 12,
  username: 'string',
  firstName: 'string',
  lastName: 'string',
  admin: true,
};

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
      ],
    }).compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a valid form', () => {
    expect(component.form.valid).toBeFalsy();

    component.form.controls['email'].setValue('test@example.com');
    component.form.controls['password'].setValue('password123');

    expect(component.form.valid).toBeTruthy();
  });

  it('should detect required fields', () => {
    const emailControl = component.form.controls['email'];
    const passwordControl = component.form.controls['password'];
    emailControl.setValue('');
    passwordControl.setValue('');
    expect(emailControl.valid).toBeFalsy();
    expect(emailControl.hasError('required')).toBeTruthy();
    expect(passwordControl.valid).toBeFalsy();
    expect(passwordControl.hasError('required')).toBeTruthy();
  });

  it('should detect invalid email', () => {
    const emailControl = component.form.controls['email'];

    emailControl.setValue('invalidemail');

    expect(emailControl.valid).toBeFalsy();
    expect(emailControl.hasError('email')).toBeTruthy();
  });
  it('should detect password length less than 3 characters', () => {
    const passwordControl = component.form.controls['password'];
    passwordControl.setValue('15');
    expect(passwordControl.valid).toBeFalsy();
    expect(passwordControl.hasError('minlength')).toBeTruthy();
  });

  it('should submit the form successfully', () => {
    jest
      .spyOn(component.authService, 'login')
      .mockReturnValue(of(mockSessionInformation));

    component.form.controls['email'].setValue('test@example.com');
    component.form.controls['password'].setValue('password123');
    component.submit();
    expect(component.onError).toBeFalsy();
  });
});
