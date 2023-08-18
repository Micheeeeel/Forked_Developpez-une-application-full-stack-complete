import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable, catchError, map } from 'rxjs';
import { Article } from 'src/app/core/models/Article';
import { ErrorHandlingService } from 'src/app/core/services/error-handling.service';

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

  addNewComment(articleCommnted: {
    comment: string;
    articleId: number;
  }): Observable<string> {
    const userId = 1;
    const commnetPayload = {
      userId: userId,
      authorName: null,
      articleId: articleCommnted.articleId,
      content: articleCommnted.comment,
      createdAt: null,
    };

    return this.http.post<any>(`${this.baseUrl}/comment`, commnetPayload).pipe(
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
}
