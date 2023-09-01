import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable, catchError, map } from 'rxjs';
import { Article } from 'src/app/core/models/Article';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';
import { ArticleWithComments } from 'src/app/core/models/ArticleWithComments';

@Injectable()
export class ArticlesService {
  public baseUrl = environment.baseUrl;

  constructor(
    private http: HttpClient,
    private errorHandlingService: ErrorHandlingService
  ) {}

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.baseUrl}/article`);
  }

  getArticleById(id: string | null): Observable<ArticleWithComments> {
    return this.http.get<ArticleWithComments>(`${this.baseUrl}/article/${id}`);
  }

  addNewComment(articleCommnted: {
    comment: string;
    articleId: number;
  }): Observable<string> {
    const userId = 1;
    const commentPayload = {
      userId: userId,
      authorName: null,
      articleId: articleCommnted.articleId,
      content: articleCommnted.comment,
      createdAt: null,
    };

    return this.http.post<any>(`${this.baseUrl}/comment`, commentPayload).pipe(
      map((response) => {
        if (response.message === 'New Comment created') {
          return 'Comment created successfully';
        } else {
          throw new Error('Failed to create Comment');
        }
      }),
      catchError(this.errorHandlingService.handleError)
    );
  }

  createArticle(articleData: {
    userId: string;
    subjectId: string;
    title: string;
    content: string;
  }): Observable<string> {
    return this.http
      .post(`${this.baseUrl}/article`, articleData, {
        responseType: 'text',
      })
      .pipe(
        map((response) => {
          if (response === 'New Article created') {
            return 'Article created successfully';
          } else {
            throw new Error('Failed to create Article');
          }
        }),
        catchError(this.errorHandlingService.handleError)
      );
  }
}
