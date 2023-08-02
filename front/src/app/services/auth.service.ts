import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private token = 'MyFakeToken';

  geToken(): string {
    return this.token;
  }
}
