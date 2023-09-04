import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlingService {
  public handleError(error: any): Observable<never> {
    let errorMessage = 'An unknown error occurred!' + error.message;
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.error && error.error.message) {
      errorMessage = `Error: ${error.error.message}`;
    }
    return throwError(errorMessage);
  }
}
