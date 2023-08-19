import { Component, Input, OnInit } from '@angular/core';
import { HeaderType } from 'src/app/app.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Input() headerType!: HeaderType;
  HeaderTypeEnum = HeaderType; // Ceci créera une référence à l'enum que nous pouvons utiliser dans le template

  constructor() {}

  ngOnInit(): void {}
}
