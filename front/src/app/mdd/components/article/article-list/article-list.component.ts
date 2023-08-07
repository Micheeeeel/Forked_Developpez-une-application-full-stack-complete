import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { Article } from 'src/app/core/models/article';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit {
  articles$!: Observable<Article[]>;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.articles$ = this.route.data.pipe(map((data) => data['articles']));
  }
}
