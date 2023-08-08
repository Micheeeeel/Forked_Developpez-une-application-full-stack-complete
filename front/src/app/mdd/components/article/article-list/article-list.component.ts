import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject, map, takeUntil } from 'rxjs';
import { Article } from 'src/app/core/models/article';
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
    console.log('this.router.navigateByUrl(/mdd/article)');

    // Navigate to an empty path first
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      // Navigate to the desired URL
      this.router.navigateByUrl('/mdd/article');
    });
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
          console.log(message); // "Subject created successfully"
          this.handleSuccess('Comment created successfully');
        },
        error: (error) => {
          this.handleError('Failed to create Comment');
        },
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
