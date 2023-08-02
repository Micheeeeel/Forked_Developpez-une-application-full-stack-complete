import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { SubjectsComponent } from './pages/subjects/subjects.component';
import { HttpClientModule } from '@angular/common/http';
import { SubjectFormComponent } from './pages/subjectform/subjectform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SubjectDetailComponent } from './pages/home/subject-detail/subject-detail.component';
import { AuthModule } from './auth/auth.module';
import { httpInterceptorProviders } from './interceptors';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SubjectsComponent,
    SubjectFormComponent,
    SubjectDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    ReactiveFormsModule,
    AuthModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
