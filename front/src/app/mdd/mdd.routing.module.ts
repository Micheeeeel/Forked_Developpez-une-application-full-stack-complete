import { NgModule } from '@angular/core';
import { SubjectsComponent } from './components/subject/subjects/subjects.component';
import { SubjectFormComponent } from './components/subject/subjectform/subjectform.component';
import { SubjectDetailComponent } from './components/subject/subject-detail/subject-detail.component';
import { AuthGuard } from '../core/guards/auth.guards';
import { RouterModule } from '@angular/router';
import { ArticleListComponent } from './components/article/article-list/article-list.component';
import { ArticlesResolver } from './resolvers/articles.resolver';

const routes = [
  { path: 'subjects', component: SubjectsComponent, canActivate: [AuthGuard] },
  {
    path: 'subjects/subject-form',
    component: SubjectFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subjects/subject-form/:id',
    component: SubjectFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subjects/:id',
    component: SubjectDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'article',
    component: ArticleListComponent,
    canActivate: [AuthGuard],
    resolve: { articles: ArticlesResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class mddRoutingModule {}
