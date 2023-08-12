import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Observable, filter, interval, observable } from 'rxjs';
import { HomeComponent } from './pages/home/home.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'front';

  showHeader = true;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}


  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      const activeComponentName = this.activatedRoute.firstChild?.snapshot.data['component'];
      const activeComponent = this.getActiveComponent(this.activatedRoute);
      if (activeComponent === HomeComponent) {
        this.showHeader = false;
      } else {
        this.showHeader = true;
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
