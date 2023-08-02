import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SubjectsComponent } from './mdd/components/subjects/subjects.component';
import { SubjectFormComponent } from './mdd/components/subjectform/subjectform.component';
import { SubjectDetailComponent } from './mdd/components/subject-detail/subject-detail.component';
import { AuthGuard } from './core/guards/auth.guards';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'subjects', component: SubjectsComponent, canActivate: [AuthGuard] },
  {
    path: 'subject-form',
    component: SubjectFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subject-form/:id',
    component: SubjectFormComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'subject/:id',
    component: SubjectDetailComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
  },

  // Redirection en cas d'URL incorrecte (404 Not Found)
  { path: '**', redirectTo: '' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
