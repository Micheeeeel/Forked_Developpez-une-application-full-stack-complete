import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation-items',
  templateUrl: './navigation-items.component.html',
  styleUrls: ['./navigation-items.component.scss'],
})
export class NavigationItemsComponent implements OnInit {
  constructor(private router: Router) {}

  ngOnInit(): void {}

  naviguerVersUserInfo() {
    this.router.navigateByUrl('/mdd/user-info');
  }
}
