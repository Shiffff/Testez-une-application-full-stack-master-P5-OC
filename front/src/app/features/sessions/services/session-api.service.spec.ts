import { HttpClientModule, HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { expect } from '@jest/globals';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule],
      providers: [SessionApiService],
    });
    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all sessions', () => {
    const mockSessions = [
      {
        id: 1,
        name: 'string',
        description: 'string',
        date: new Date(),
        teacher_id: 1,
        users: [],
        createdAt: new Date(),
        updatedAt: new Date(),
      },
      {
        id: 2,
        name: 'string',
        description: 'string',
        date: new Date(),
        teacher_id: 1,
        users: [],
        createdAt: new Date(),
        updatedAt: new Date(),
      },
    ];

    service.all().subscribe((sessions) => {
      expect(sessions).toEqual(mockSessions);
    });

    const request = httpMock.expectOne('api/session');
    expect(request.request.method).toBe('GET');

    request.flush(mockSessions);
  });

  it('should fetch session details', () => {
    const mockSession = { id: '1', name: 'Test Session' };

    service.detail('1').subscribe((session) => {
      expect(session).toEqual(mockSession);
    });

    const request = httpMock.expectOne('api/session/1');
    expect(request.request.method).toBe('GET');

    request.flush(mockSession);
  });

  it('should delete session', () => {
    service.delete('1').subscribe();

    const request = httpMock.expectOne('api/session/1');
    expect(request.request.method).toBe('DELETE');

    request.flush({});
  });

  it('should participate in session', () => {
    service.participate('1', '1').subscribe();

    const request = httpMock.expectOne('api/session/1/participate/1');
    expect(request.request.method).toBe('POST');

    request.flush({});
  });

  it('should unparticipate in session', () => {
    service.unParticipate('1', '1').subscribe();

    const request = httpMock.expectOne('api/session/1/participate/1');
    expect(request.request.method).toBe('DELETE');

    request.flush({});
  });
});
