import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { Article } from 'src/app/core/models/article';
import { ArticlesService } from 'src/app/mdd/services/articles.service';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit {
  articles$!: Observable<Article[]>;

  constructor(
    private route: ActivatedRoute,
    private service: ArticlesService
  ) {}

  ngOnInit(): void {
    this.articles$ = this.route.data.pipe(map((data) => data['articles']));
  }

  onArticleCommented(articleCommented: { comment: string; articleId: number }) {
    this.service.addNewComment(articleCommented);
  }
}
