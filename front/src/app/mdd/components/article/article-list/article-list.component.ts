import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  Observable,
  Subject,
  catchError,
  map,
  takeUntil,
  throwError,
} from 'rxjs';
import { Article } from 'src/app/core/models/Article';
import { ArticlesService } from 'src/app/mdd/services/articles.service';

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

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: ArticlesService
  ) {}

  ngOnInit(): void {
    this.articles$ = this.route.data.pipe(map((data) => data['articles']));
  }

  handleSuccess(message: string) {
    console.log(message);
    this.message = message;
  }

  handleError(message: string) {
    console.error(message);
    this.errorMessage = message;
  }

  onArticleCommented(articleCommented: { comment: string; articleId: number }) {
    this.service
      .addNewComment(articleCommented)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (message) => {
          this.handleSuccess('Comment created successfully');

          // Reload the articles
          this.articles$ = this.reloadArticles();
        },
        error: (error) => {
          this.handleError('Failed to create Comment');
        },
      });
  }

  private reloadArticles(): Observable<Article[]> {
    return this.service.getArticles().pipe(
      catchError((error) => {
        console.error(error);
        this.errorMessage = 'Error fetching articles';
        return throwError(() => new Error('Error fetching articles'));
      })
    );
  }

  CreateArticle() {
    throw new Error('Method not implemented.');
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
