import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';

import { SubjectService } from './subject.service';
import { Subject } from '../models/Subject';

describe('SubjectService', () => {
  let service: SubjectService; // service we want to test
  let httpMock: HttpTestingController; // mock of the http service

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // import the HttpClientTestingModule which provides the HttpTestingController
      providers: [SubjectService], // provide the service we want to test
    }); // configure the TestBed module

    service = TestBed.inject(SubjectService); // inject the service we want to test
    httpMock = TestBed.inject(HttpTestingController); // inject the mock of the http service
  });

  afterEach(() => {
    httpMock.verify(); // verify that there are no outstanding requests
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve all subjects from the API via GET', () => {
    const mockSubjects: Subject[] = [
      { id: 1, name: 'Subject 1' },
      { id: 2, name: 'Subject 2' },
    ];

    service.getSubjects().subscribe((subjects: Subject[]) => {
      expect(subjects.length).toBe(2);
      expect(subjects).toEqual(mockSubjects);
    }); // subscribe to the observable returned by the service

    const request = httpMock.expectOne(`${service.baseUrl}/subjects`); // expect that a request will be made to the specified URL via GET
    expect(request.request.method).toBe('GET'); // expect that the request will be made via GET

    request.flush(mockSubjects); // provide mock data to be returned from the request
  });
});
