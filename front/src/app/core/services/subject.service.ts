import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Subject } from '../models/Subject';
import { environment } from '../../../environments/environment';
import { ErrorHandlingService } from './error-handling.service';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  public baseUrl = environment.baseUrl;

  constructor(
    private http: HttpClient,
    private errorHandlingService: ErrorHandlingService
  ) {}

  getSubjects(): Observable<Subject[]> {
    return this.http
      .get<Subject[]>(`${this.baseUrl}/subject`)
      .pipe(catchError(this.errorHandlingService.handleError));
  }

  getSubjectById(subjectId: string): Observable<Subject> {
    return this.http
      .get<Subject>(`${this.baseUrl}/subject/${subjectId}`)
      .pipe(catchError(this.errorHandlingService.handleError));
  }

  addSubject(formValue: { name: string }): Observable<string> {
    return this.http
      .post(`${this.baseUrl}/subject`, formValue, { responseType: 'text' })
      .pipe(
        map((response) => {
          if (response === 'New Subject created') {
            return 'Subject created successfully';
          } else {
            throw new Error('Failed to create Subject');
          }
        }),
        catchError(this.errorHandlingService.handleError)
      );
  }

  deleteSubject(subjectId: string): Observable<string> {
    const url = `${this.baseUrl}/subject/${subjectId}`;

    return this.http
      .delete<string>(url)
      .pipe(catchError(this.errorHandlingService.handleError));
  }

  updateSubject(id: string, formValue: { name: string }): Observable<string> {
    return this.http
      .put(`${this.baseUrl}/subject/${id}`, formValue, { responseType: 'text' })
      .pipe(
        map((response) => {
          if (response === 'Subject updated') {
            return 'Subject updated successfully';
          } else {
            throw new Error('Failed to update Subject');
          }
        }),
        catchError(this.errorHandlingService.handleError)
      );
  }
}
