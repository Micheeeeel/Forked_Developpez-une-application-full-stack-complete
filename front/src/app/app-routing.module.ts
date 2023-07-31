import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SubjectsComponent } from './pages/subjects/subjects.component';
import { NewSubjectComponent } from './pages/new-subject/new-subject.component';
import { SubjectDetailComponent } from './pages/home/subject-detail/subject-detail.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'subjects', component: SubjectsComponent },
  { path: 'create_subject', component: NewSubjectComponent },
  { path: 'subject/:id', component: SubjectDetailComponent },

  // Redirection en cas d'URL incorrecte (404 Not Found)
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
