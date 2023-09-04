import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationItemsComponent } from './navigation-items.component';

describe('NavigationItemsComponent', () => {
  let component: NavigationItemsComponent;
  let fixture: ComponentFixture<NavigationItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavigationItemsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavigationItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
