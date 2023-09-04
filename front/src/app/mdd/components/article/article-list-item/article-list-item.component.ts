import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Article } from '../../../../core/models/Article';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-list-item',
  templateUrl: './article-list-item.component.html',
  styleUrls: ['./article-list-item.component.scss'],
})
export class ArticleListItemComponent implements OnInit {
  @Input() article!: Article;

  constructor(private router: Router) {}

  ngOnInit(): void {}

  showArticleDetails() {
    this.router.navigate(['mdd/article/article-detail/', this.article.id]);
    //console.log('showArticleDetails');
  }
}
