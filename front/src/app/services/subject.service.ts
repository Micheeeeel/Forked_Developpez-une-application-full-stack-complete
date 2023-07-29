import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
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
}
