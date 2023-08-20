import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subject as MySubject } from 'src/app/core/models/Subject';

@Component({
  selector: 'app-subject-list-item',
  templateUrl: './subject-list-item.component.html',
  styleUrls: ['./subject-list-item.component.scss'],
})
export class SubjectListItemComponent implements OnInit {
  @Input() subject!: MySubject;
  @Output() subscribeEvent = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}
}
