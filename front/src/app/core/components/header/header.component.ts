import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { HeaderType } from 'src/app/app.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  @Output() toggleSidenav = new EventEmitter<void>();
  @Input() headerType!: HeaderType;
  HeaderTypeEnum = HeaderType; // Ceci créera une référence à l'enum que nous pouvons utiliser dans le template

  constructor(private cdRef: ChangeDetectorRef) {}

  ngOnInit(): void {}

  onMenuClick() {
    this.toggleSidenav.emit();
    this.cdRef.detectChanges();
  }
}
