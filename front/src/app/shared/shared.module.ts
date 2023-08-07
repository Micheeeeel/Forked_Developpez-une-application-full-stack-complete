import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CommentsComponent } from './components/comments/comments.component';
import { MaterialModule } from './material.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [CommentsComponent],
  imports: [CommonModule, MaterialModule, ReactiveFormsModule],
  exports: [CommentsComponent, MaterialModule, ReactiveFormsModule],
})
export class SharedModule {}
