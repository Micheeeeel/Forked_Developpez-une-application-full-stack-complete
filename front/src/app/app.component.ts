import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Observable, filter, interval, observable } from 'rxjs';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './auth/components/login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'front';

  headerType!: HeaderType;
  HeaderTypeEnum = HeaderType; // Ceci créera une référence à l'enum que nous pouvons utiliser dans le template

  showHeader = true;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe(() => {
        const activeComponent = this.getActiveComponent(this.activatedRoute);
        if (activeComponent === HomeComponent) {
          this.headerType = HeaderType.HomeHeader;
        } else if (
          activeComponent === LoginComponent ||
          activeComponent === LoginComponent // a remplacer par le future register component
        ) {
          // check if the component is the login component or the regi
          this.headerType = HeaderType.LoginHeader;
        } else {
          this.headerType = HeaderType.MddHeader;
        }
      });
  }

  // tente de trouver le composant associé à la route donnée. Si la route n'a pas
  // de composant (comme c'est le cas pour une route avec des routes enfants),
  // elle tente de le trouver récursivement en parcourant les routes enfants.
  getActiveComponent(route: ActivatedRoute): any {
    if (route.routeConfig && route.routeConfig.component) {
      return route.routeConfig.component;
    } else if (route.firstChild) {
      return this.getActiveComponent(route.firstChild);
    }
    return null;
  }
}

export enum HeaderType {
  HomeHeader,
  LoginHeader,
  MddHeader,
}
