import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectListItemComponent } from './subject-list-item.component';

describe('SubjectListItemComponent', () => {
  let component: SubjectListItemComponent;
  let fixture: ComponentFixture<SubjectListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
