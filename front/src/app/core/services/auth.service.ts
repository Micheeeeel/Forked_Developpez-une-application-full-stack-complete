import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ErrorHandlingService } from './error-handling.service';
import { Token } from '../../core/models/Token';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public baseUrl = environment.baseUrl;

  // private token!: string;

  constructor(
    private http: HttpClient,
    private errorHandlingService: ErrorHandlingService
  ) {}

  public register(formValue: { name: string }): Observable<Token> {
    return this.http.post<Token>(`${this.baseUrl}/auth/register`, formValue);
  }

  public login(formValue: { name: string }): Observable<Token> {
    return this.http.post<Token>(`${this.baseUrl}/auth/login`, formValue);
  }

  // register(formValue: { name: string }): Observable<string> {
  //   return this.http
  //     .post<Token>(`${this.baseUrl}/user`, formValue, {
  //       responseType: 'text',
  //     })
  //     .pipe(
  //       map((response) => {
  //         if (response === 'New User created') {
  //           this.token = 'myFakeToken';
  //           return 'User created successfully';
  //         } else {
  //           throw new Error('Failed to create User');
  //         }
  //       }),
  //       catchError(this.errorHandlingService.handleError)
  //     );
  // }
}
