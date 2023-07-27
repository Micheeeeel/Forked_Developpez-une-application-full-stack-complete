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
  destroy$: Subject<boolean> = new Subject<boolean>();

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.getSubjects();

    this.destroy$ = new Subject<boolean>();
  }

  getSubjects(): void {
    // use interval to test the unsubscribe
    interval(1000).pipe(takeUntil(this.destroy$), tap(console.log)).subscribe();

    this.subjectService
      .getSubjects()
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        (subjects) => {
          this.subjects = subjects;
        },
        (error) => {
          this.errorMessage = 'Error fetching subjects';
        }
      );
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
    // Unsubscribe from the subject$ observable
    this.destroy$.unsubscribe();
  }
}
