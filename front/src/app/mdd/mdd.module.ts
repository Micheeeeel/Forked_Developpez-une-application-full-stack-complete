import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectsComponent } from './components/subjects/subjects.component';
import { SubjectFormComponent } from './components/subjectform/subjectform.component';
import { SubjectDetailComponent } from './components/subject-detail/subject-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { mddRoutingModule } from './mdd.routing.module';

@NgModule({
  declarations: [
    SubjectsComponent,
    SubjectFormComponent,
    SubjectDetailComponent,
  ],
  imports: [CommonModule, ReactiveFormsModule, mddRoutingModule],
  exports: [SubjectsComponent, SubjectFormComponent, SubjectDetailComponent],
})
export class MddModule {}
