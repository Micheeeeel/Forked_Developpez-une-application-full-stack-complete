import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectsComponent } from './components/subject/subjects/subjects.component';
import { SubjectFormComponent } from './components/subject/subjectform/subjectform.component';
import { SubjectDetailComponent } from './components/subject/subject-detail/subject-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { mddRoutingModule } from './mdd.routing.module';
import { ArticlesService } from './services/articles.service';
import { ArticlesResolver } from './resolvers/articles.resolver';
import { ArticleListComponent } from './components/article/article-list/article-list.component';
import { ArticleListItemComponent } from './components/article/article-list-item/article-list-item.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    SubjectsComponent,
    SubjectFormComponent,
    SubjectDetailComponent,
    ArticleListComponent,
    ArticleListItemComponent,
  ],
  imports: [CommonModule, SharedModule, ReactiveFormsModule, mddRoutingModule],
  exports: [SubjectsComponent, SubjectFormComponent, SubjectDetailComponent],
  providers: [ArticlesService, ArticlesResolver],
})
export class MddModule {}
