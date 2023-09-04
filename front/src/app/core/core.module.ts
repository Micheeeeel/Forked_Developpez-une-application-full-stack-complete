import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { httpInterceptorProviders } from './interceptors';
import { HeaderComponent } from './components/header/header.component';
import { RouterModule } from '@angular/router';
import fr from '@angular/common/locales/fr';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';
import { NavigationItemsComponent } from './components/navigation-items/navigation-items.component';

@NgModule({
  declarations: [HeaderComponent, NavigationItemsComponent],
  imports: [
    CommonModule, // nécessaire pour tout module qui déclare des composants (directives, pipes, etc.)
    RouterModule,
    HttpClientModule,
    SharedModule,
  ],
  exports: [HeaderComponent, NavigationItemsComponent],
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
