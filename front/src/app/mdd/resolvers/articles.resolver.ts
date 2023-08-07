import { Injectable } from '@angular/core';
import {
  Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot,
} from '@angular/router';
import { Article } from 'src/app/core/models/article';
import { ArticlesService } from '../services/articles.service';
import { Observable } from 'rxjs';

@Injectable()
export class ArticlesResolver implements Resolve<Article[]> {
  constructor(private articlesService: ArticlesService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<Article[]> {
    return this.articlesService.getArticles();
  }
}
