import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Article } from '../../../../core/models/Article';

@Component({
  selector: 'app-article-list-item',
  templateUrl: './article-list-item.component.html',
  styleUrls: ['./article-list-item.component.scss'],
})
export class ArticleListItemComponent implements OnInit {
  @Input() article!: Article;
  @Output() articleCommented = new EventEmitter<{
    articleId: number;
    comment: string;
  }>();

  constructor() {}

  ngOnInit(): void {}

  onNewComment(comment: string) {
    this.articleCommented.emit({ articleId: this.article.id, comment });
  }

  showArticleDetails(article: Article) {
    // Votre logique pour afficher les détails de l'article.
    // Cela pourrait inclure la navigation vers un autre composant ou l'affichage d'une boîte de dialogue, par exemple.
  }
}
