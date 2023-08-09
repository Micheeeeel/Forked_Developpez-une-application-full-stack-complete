import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ErrorHandlingService } from './error-handling.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public baseUrl = environment.baseUrl;

  private token!: string;

  constructor(
    private http: HttpClient,
    private errorHandlingService: ErrorHandlingService
  ) {}

  createUser(formValue: { name: string }): Observable<string> {
    return this.http
      .post(`${this.baseUrl}/user`, formValue, {
        responseType: 'text',
      })
      .pipe(
        map((response) => {
          if (response === 'New User created') {
            this.token = 'myFakeToken';
            return 'User created successfully';
          } else {
            throw new Error('Failed to create User');
          }
        }),
        catchError(this.errorHandlingService.handleError)
      );
  }

  login(): void {
    this.token = 'myFakeToken';
  }

  getToken(): string {
    return this.token;
  }
}
