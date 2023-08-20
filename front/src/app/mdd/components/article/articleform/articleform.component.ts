import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ArticlesService } from '../../../services/articles.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-articleform',
  templateUrl: './articleform.component.html',
  styleUrls: ['./articleform.component.scss'],
})
export class ArticleformComponent implements OnInit {
  constructor(
    private articleService: ArticlesService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onCreateArticle(form: NgForm) {
    if (form.valid) {
      this.articleService.createArticle(form.value).subscribe({
        next: (message) => {
          console.log('Article created successfully');
          this.router.navigate(['/path-where-you-want-to-redirect']); // Ajustez le chemin
        },
        error: (error) => {
          console.error('Failed to create article', error);
        },
      });
    }
  }
}
