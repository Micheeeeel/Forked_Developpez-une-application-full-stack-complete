import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ArticlesService } from '../../../services/articles.service';
import { Router } from '@angular/router';
import { Subject as MySubject } from '../../../../core/models/Subject';
import { SubjectService } from 'src/app/core/services/subject.service';

@Component({
  selector: 'app-articleform',
  templateUrl: './articleform.component.html',
  styleUrls: ['./articleform.component.scss'],
})
export class ArticleformComponent implements OnInit {
  themes: MySubject[] = []; // Remplacez MyTheme par le type de votre modèle de thème si différent
  selectedTheme!: string; // Pour stocker le thème sélectionné
  message: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private articleService: ArticlesService,
    private router: Router,
    private subjectService: SubjectService
  ) {}

  ngOnInit() {
    this.subjectService.getSubjects().subscribe({
      next: (subjects) => {
        this.themes = subjects;
      },
      error: (error) => {
        console.error('Failed to load subjects', error);
      },
    });
  }

  handleSuccess(message: string) {
    console.log(message);
    this.message = message;
    this.router.navigateByUrl('/mdd/article');
  }

  handleError(message: string) {
    console.error(message);
    this.errorMessage = message;
  }

  onCreateArticle(form: NgForm) {
    if (form.valid) {
      const articleData = {
        userId: '1', // A remplacer par l'ID de l'utilisateur connecté
        subjectId: form.value.theme,
        title: form.value.title,
        content: form.value.content,
      };

      this.articleService.createArticle(articleData).subscribe({
        next: (message) => {
          this.handleSuccess('Article created successfully');
        },
        error: (error) => {
          this.handleError('Failed to create Article');
        },
      });
    }
  }
}
