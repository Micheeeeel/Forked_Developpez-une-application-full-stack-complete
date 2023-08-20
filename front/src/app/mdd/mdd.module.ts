import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectsComponent } from './components/subject/subjects/subjects.component';
import { SubjectFormComponent } from './components/subject/subjectform/subjectform.component';
import { SubjectDetailComponent } from './components/subject/subject-detail/subject-detail.component';
import { mddRoutingModule } from './mdd.routing.module';
import { ArticlesService } from './services/articles.service';
import { ArticlesResolver } from './resolvers/articles.resolver';
import { ArticleListComponent } from './components/article/article-list/article-list.component';
import { ArticleListItemComponent } from './components/article/article-list-item/article-list-item.component';
import { SharedModule } from '../shared/shared.module';
import { SubjectListItemComponent } from './components/subject/subject-list-item/subject-list-item.component';
import { ArticleformComponent } from './components/article/articleform/articleform.component';

@NgModule({
  declarations: [
    SubjectsComponent,
    SubjectFormComponent,
    SubjectDetailComponent,
    ArticleListComponent,
    ArticleListItemComponent,
    SubjectListItemComponent,
    ArticleformComponent,
  ],
  imports: [CommonModule, SharedModule, mddRoutingModule],
  exports: [SubjectsComponent, SubjectFormComponent, SubjectDetailComponent],
  providers: [ArticlesService, ArticlesResolver],
})
export class MddModule {}
