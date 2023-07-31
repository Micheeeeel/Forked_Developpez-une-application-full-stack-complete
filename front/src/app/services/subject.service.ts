import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Subject } from '../models/Subject';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  public baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getSubjects(): Observable<Subject[]> {
    return this.http.get<Subject[]>(`${this.baseUrl}/subject`);
  }

  getSubjectById(subjectId: string): Observable<Subject> {
    return this.http.get<Subject>(`${this.baseUrl}/subject/${subjectId}`);
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
        })
      );
  }

  deleteSubject(subjectId: string): Observable<string> {
    const url = `${this.baseUrl}/subject/${subjectId}`;

    return this.http.delete<string>(url).pipe(
      catchError((error) => {
        console.error('Erreur lors de la suppression du sujet :', error);
        throw new Error(error);
      })
    );
  }

  editSubject(id: string): Observable<string> {
    throw new Error('Method not implemented.');
  }
}
