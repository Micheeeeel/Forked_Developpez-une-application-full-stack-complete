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

    const request = httpMock.expectOne(`${service.baseUrl}/subject`); // expect that a request will be made to the specified URL via GET
    expect(request.request.method).toBe('GET'); // expect that the request will be made via GET

    request.flush(mockSubjects); // provide mock data to be returned from the request
  });

  it('should add a new subject via POST', () => {
    const formValue = { name: 'New Subject' };
    const mockResponse = 'New Subject created';

    service.addSubject(formValue).subscribe((response: string) => {
      expect(response).toBe('Subject created successfully');
    });

    const request = httpMock.expectOne(`${service.baseUrl}/subject`);
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(formValue);

    request.flush(mockResponse, { status: 201, statusText: 'Created' });
  });

  it('should handle an error when adding a subject', () => {
    const formValue = { name: 'New Subject' };
    const mockErrorResponse = 'Failed to create Subject';

    service.addSubject(formValue).subscribe(
      () => fail('should have thrown an error'),
      (error: any) => {
        expect(error.message).toBe('Failed to create Subject');
      }
    );

    const request = httpMock.expectOne(`${service.baseUrl}/subject`);
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(formValue);

    request.flush(mockErrorResponse, {
      status: 500,
      statusText: 'Internal Server Error',
    });
  });

  it('should retrieve a subject by ID from the API via GET', () => {
    const mockSubjectId = '1'; // Corrected type to string
    const mockSubject: Subject = { id: 1, name: 'Subject 1' }; // Assuming the Subject model has an id of type number

    service.getSubjectById(mockSubjectId).subscribe((subject: Subject) => {
      expect(subject).toEqual(mockSubject);
    });

    const request = httpMock.expectOne(
      `${service.baseUrl}/subject/${mockSubjectId}`
    );
    expect(request.request.method).toBe('GET');

    request.flush(mockSubject);
  });

  it('should delete a subject by ID via DELETE', () => {
    const mockSubjectId = '1';
    const mockResponse = 'Subject deleted successfully';

    // subscribe to the observable returned by the service
    service.deleteSubject(mockSubjectId).subscribe((response: string) => {
      expect(response).toBe(mockResponse); // expect a success because the mocked request (flush) is successful
    });

    // expect that a request will be made to the specified URL via DELETE
    const request = httpMock.expectOne(
      `${service.baseUrl}/subject/${mockSubjectId}`
    );

    // expect that the request will be made via DELETE
    expect(request.request.method).toBe('DELETE');

    // mock the response with the status code and status text
    request.flush(mockResponse, {
      status: 200,
      statusText: 'OK',
    });
  });

  it('should handle an error when deleting a subject', () => {
    const mockSubjectId = '1';
    const mockErrorResponse = 'Failed to delete Subject';

    service.deleteSubject(mockSubjectId).subscribe(
      () => fail('should have thrown an error'),
      (error: any) => {
        expect(error.message).toBe('Failed to delete Subject');
      }
    );

    const request = httpMock.expectOne(
      `${service.baseUrl}/subject/${mockSubjectId}`
    );
    expect(request.request.method).toBe('DELETE');

    request.flush(mockErrorResponse, {
      status: 500,
      statusText: 'Internal Server Error',
    });
  });

  it('should update a subject via PUT', () => {
    const mockSubjectId = '1';
    const formValue = { name: 'Updated Subject' };
    const mockResponse = 'Subject updated';

    service
      .updateSubject(mockSubjectId, formValue)
      .subscribe((response: string) => {
        expect(response).toBe('Subject updated successfully');
      });

    const request = httpMock.expectOne(
      `${service.baseUrl}/subject/${mockSubjectId}`
    );
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual(formValue);

    request.flush(mockResponse);
  });

  it('should handle an error when updating a subject', () => {
    const mockSubjectId = '1';
    const formValue = { name: 'Updated Subject' };
    const mockErrorResponse = 'Failed to update Subject';

    service.updateSubject(mockSubjectId, formValue).subscribe({
      next: () => fail('should have thrown an error'),
      error: (error: any) => {
        expect(error.message).toBe('Failed to update Subject');
      },
    });

    const request = httpMock.expectOne(
      `${service.baseUrl}/subject/${mockSubjectId}`
    );
    expect(request.request.method).toBe('PUT');
    expect(request.request.body).toEqual(formValue);

    request.flush(mockErrorResponse, {
      status: 500,
      statusText: 'Internal Server Error',
    });
  });
});
