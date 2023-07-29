import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject as MySubject } from 'src/app/models/Subject';
import { SubjectService } from '../../services/subject.service';
import { Observable, Subject, interval } from 'rxjs';
import { takeUntil, tap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-subjects-component',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit, OnDestroy {
  subjects: MySubject[] = [];
  errorMessage: string | null = null;

  // Separate Subjects for each subscription
  private getSubjectsDestroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private subjectService: SubjectService, private router: Router) {}

  ngOnInit(): void {
    this.getSubjects();
  }

  getSubjects(): void {
    // use interval to test the unsubscribe
    interval(1000)
      .pipe(tap(console.log), takeUntil(this.getSubjectsDestroy$))
      .subscribe();

    this.subjectService
      .getSubjects()
      .pipe(
        takeUntil(this.getSubjectsDestroy$),
        tap({
          next: (subjects) => {
            this.subjects = subjects;
          },
          error: (error) => {
            this.errorMessage = 'Error fetching subjects';
          },
        })
      )
      .subscribe();
  }

  onAddNewSubject(): void {
    this.router.navigateByUrl('/create_subject');
  }

  ngOnDestroy(): void {
    this.getSubjectsDestroy$.next(true);

    // Unsubscribe from the subjectService
    this.getSubjectsDestroy$.unsubscribe();
  }
}
