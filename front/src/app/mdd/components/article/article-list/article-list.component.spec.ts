import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleListComponent } from './article-list.component';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';

describe('ArticleListComponent', () => {
  let component: ArticleListComponent;
  let fixture: ComponentFixture<ArticleListComponent>;
  let activatedRoute: ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ArticleListComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              articles: [
                /* vos articles simulés ici */
              ],
            }), // Utilisez `of` pour simuler un Observable
            snapshot: { paramMap: { get: jest.fn() } },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ArticleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticleListComponent);
    component = fixture.componentInstance;

    activatedRoute = TestBed.inject(ActivatedRoute);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
