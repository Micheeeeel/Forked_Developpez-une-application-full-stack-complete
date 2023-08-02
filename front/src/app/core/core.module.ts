import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { httpInterceptorProviders } from './interceptors';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';
import fr from '@angular/common/locales/fr';

@NgModule({
  declarations: [HeaderComponent],
  imports: [
    CommonModule, // nécessaire pour tout module qui déclare des composants (directives, pipes, etc.)
    RouterModule,
  ],
  exports: [HeaderComponent],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    httpInterceptorProviders,
  ],
})
export class CoreModule {
  constructor() {
    registerLocaleData(fr);
  }
}
