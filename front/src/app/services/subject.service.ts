import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Subject } from '../models/Subject';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  public baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  getSubjects(): Observable<Subject[]> {
    return this.http
      .get<Subject[]>(`${this.baseUrl}/subject`)
      .pipe(catchError(this.handleError));
  }

  getSubjectById(subjectId: string): Observable<Subject> {
    return this.http
      .get<Subject>(`${this.baseUrl}/subject/${subjectId}`)
      .pipe(catchError(this.handleError));
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
        catchError(this.handleError)
      );
  }

  deleteSubject(subjectId: string): Observable<string> {
    const url = `${this.baseUrl}/subject/${subjectId}`;

    return this.http.delete<string>(url).pipe(catchError(this.handleError));
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
        catchError(this.handleError)
      );
  }

  // Error handling
  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `Server-side error: ${error.status} ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
