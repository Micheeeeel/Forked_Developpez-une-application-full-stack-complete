import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SubscriptionService {
  // Ajuster l'URL selon votre API
  public baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  isSubscribed(subjectId: number, userId: number = 1) {
    // Appel à l'API pour vérifier si l'utilisateur est abonné au sujet
    return this.http.get<boolean>(
      `${this.baseUrl}/subscription/${subjectId}/${userId}`
    );
  }

  subscribeToSubject(subjectId: number, userId: number = 1) {
    // Appel à l'API pour s'abonner au sujet
    return this.http.post(`${this.baseUrl}/subscription/subscribe`, {
      subjectId,
      userId,
    });
  }

  unsubscribeSubject(subjectId: number, userId: number = 1) {
    // Appel à l'API pour se désabonner du sujet
    return this.http.delete(
      `${this.baseUrl}/subscription/unsubscribe/${subjectId}/${userId}`
    );
  }
}
