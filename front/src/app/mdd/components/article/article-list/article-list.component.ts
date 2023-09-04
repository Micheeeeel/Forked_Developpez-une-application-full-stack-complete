import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject, map } from 'rxjs';
import { Article } from 'src/app/core/models/Article';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit, OnDestroy {
  articles$!: Observable<Article[]>;
  message: string | null = null;
  errorMessage: string | null = null;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.articles$ = this.route.data.pipe(map((data) => data['articles']));
  }

  CreateArticle() {
    this.router.navigateByUrl('/mdd/article/article-form');
  }

  sortArticles(criterion: 'title' | 'content' | 'publishedAt') {
    this.articles$ = this.articles$.pipe(
      map((articles: Article[]) =>
        articles.sort((a, b) => {
          if (a[criterion] > b[criterion]) {
            return 1;
          }
          if (a[criterion] < b[criterion]) {
            return -1;
          }
          return 0;
        })
      )
    );
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
