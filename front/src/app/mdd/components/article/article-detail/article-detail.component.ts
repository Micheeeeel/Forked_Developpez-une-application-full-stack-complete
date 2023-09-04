import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, catchError, takeUntil, throwError } from 'rxjs';
import { Article } from 'src/app/core/models/Article';
import { ArticleWithComments } from 'src/app/core/models/ArticleWithComments';
import { ArticlesService } from 'src/app/mdd/services/articles.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent implements OnInit {
  articleId!: string | null;
  article$!: Observable<ArticleWithComments>;
  message: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService
  ) {}

  ngOnInit(): void {
    this.articleId = this.route.snapshot.paramMap.get('id');
    if (this.articleId != null) {
      this.article$ = this.articleService.getArticleById(this.articleId);
    } else {
      console.log('articleId is null');
    }
  }

  handleNewComment(comment: string) {
    const articleCommented = { comment, articleId: Number(this.articleId) };
    this.onArticleCommented(articleCommented);
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
    this.articleService.addNewComment(articleCommented).subscribe({
      next: (message) => {
        this.handleSuccess('Comment created successfully');

        // Reload the articles
        this.article$ = this.reloadArticles();
      },
      error: (error) => {
        this.handleError('Failed to create Comment');
      },
    });
  }

  private reloadArticles(): Observable<ArticleWithComments> {
    return this.articleService.getArticleById(this.articleId).pipe(
      catchError((error) => {
        console.error(error);
        this.errorMessage = 'Error fetching articles';
        return throwError(() => new Error('Error fetching articles'));
      })
    );
  }
}
