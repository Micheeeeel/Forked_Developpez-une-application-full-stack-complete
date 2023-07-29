import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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

  addSubject(formValue: { name: string }): void {
    this.http
      .post(`${this.baseUrl}/subject`, formValue)
      .subscribe((response) => {
        console.log(response);
      });
  }
}
