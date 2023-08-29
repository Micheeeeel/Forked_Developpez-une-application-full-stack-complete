import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ErrorHandlingService } from './error-handling.service';
import { Token } from '../../core/models/Token';
import { User } from '../models/User';

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
    console.log('ça envoie ce qui suit: ' + formValue.name);

    return this.http.post<Token>(`${this.baseUrl}/auth/register`, formValue);
  }

  public login(formValue: { name: string }): Observable<Token> {
    return this.http.post<Token>(`${this.baseUrl}/auth/login`, formValue);
  }

  public getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/user/me`);
  }

  public updateUser(
    userId: string,
    formValue: { name: string }
  ): Observable<string> {
    return this.http.put(`${this.baseUrl}/user/${userId}`, formValue, {
      responseType: 'text',
    });
  }
}
