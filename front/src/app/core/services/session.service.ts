import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public user!: User;

  public logIn(user: User): void {
    this.user = user;
    this.isLogged = true;
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.isLogged = false;
  }
}
