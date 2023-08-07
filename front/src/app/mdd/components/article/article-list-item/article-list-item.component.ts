import { Component, Input, OnInit } from '@angular/core';
import { Article } from 'src/app/core/models/article';

@Component({
  selector: 'app-article-list-item',
  templateUrl: './article-list-item.component.html',
  styleUrls: ['./article-list-item.component.scss'],
})
export class ArticleListItemComponent implements OnInit {
  @Input() article!: Article;

  constructor() {}

  ngOnInit(): void {}
}
