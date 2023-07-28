import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject as MySubject } from 'src/app/models/Subject';
import { SubjectService } from '../../services/subject.service';
import { Observable, Subject, interval } from 'rxjs';
import { takeUntil, tap } from 'rxjs/operators';

@Component({
  selector: 'app-subjects-component',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit, OnDestroy {
  subjects: MySubject[] = [];
  errorMessage: string | null = null;
  // add a rxjs Subject to manage the subscription - make it different than the Subject model
  private destroy$!: Subject<boolean>;

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.destroy$ = new Subject<boolean>();

    this.getSubjects();
  }

  getSubjects(): void {
    // use interval to test the unsubscribe
    interval(1000).pipe(tap(console.log), takeUntil(this.destroy$)).subscribe();

    this.subjectService
      .getSubjects()
      .pipe(
        takeUntil(this.destroy$),
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

  ngOnDestroy(): void {
    this.destroy$.next(true);

    // Unsubscribe from the subjectService
    this.destroy$.unsubscribe();
  }
}
