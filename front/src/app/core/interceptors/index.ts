import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth.interceptor';

// L'intercepteur est fourni comme un service dans le système d'injection de dépendances d'Angular, ce qui permet à toutes les requêtes HTTP d'utiliser cet intercepteur.
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
];
