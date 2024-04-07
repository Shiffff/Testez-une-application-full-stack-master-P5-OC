import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { Observable } from 'rxjs';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with isLogged set to false', () => {
    expect(service.isLogged).toBeFalsy();
  });
  it('should return Observable<boolean> when $isLogged is called', () => {
    const isLogged$ = service.$isLogged();
    expect(isLogged$).toBeTruthy();
    expect(isLogged$ instanceof Observable).toBeTruthy();
  });

  it('should set isLogged to true when logIn is called', () => {
    const mockUser: SessionInformation = {
      token: 'string',
      type: 'string',
      id: 4,
      username: 'string',
      firstName: 'string',
      lastName: 'string',
      admin: false,
    };
    service.logIn(mockUser);
    expect(service.isLogged).toBeTruthy();
  });

  it('should set sessionInformation when logIn is called', () => {
    const mockUser: SessionInformation = {
      token: 'string',
      type: 'string',
      id: 4,
      username: 'string',
      firstName: 'string',
      lastName: 'string',
      admin: false,
    };
    service.logIn(mockUser);
    expect(service.sessionInformation).toEqual(mockUser);
  });
});
