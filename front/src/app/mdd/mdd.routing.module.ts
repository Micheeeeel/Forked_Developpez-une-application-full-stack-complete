import { NgModule } from '@angular/core';
import { SubjectsComponent } from './components/subjects/subjects.component';
import { SubjectFormComponent } from './components/subjectform/subjectform.component';
import { SubjectDetailComponent } from './components/subject-detail/subject-detail.component';
import { AuthGuard } from '../core/guards/auth.guards';
import { RouterModule } from '@angular/router';

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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class mddRoutingModule {}
