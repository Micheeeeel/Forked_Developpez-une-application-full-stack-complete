import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject as MySubject } from 'src/app/core/models/Subject';
import { SubjectService } from '../../../../core/services/subject.service';
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

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(private subjectService: SubjectService, private router: Router) {}

  ngOnInit(): void {
    this.getSubjects();
  }

  getSubjects(): void {
    // // use interval to test the unsubscribe
    // interval(1000)
    //   .pipe(tap(console.log), takeUntil(this.unsubscribe$))
    //   .subscribe();

    this.subjectService
      .getSubjects()
      .pipe(
        tap({
          next: (subjects) => {
            this.subjects = subjects;
          },
          error: (error) => {
            console.error(error);
            this.errorMessage = 'Error fetching subjects';
          },
        }),
        takeUntil(this.unsubscribe$)
      )
      .subscribe();
  }

  onDeleteSubject(subjectId: string): void {
    this.subjectService
      .deleteSubject(subjectId)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (message) => {
          console.log(message); // "Subject delete successfully"
          this.getSubjects();
        },
        error: (error) => {
          console.error(error);
          this.errorMessage = 'Error deleting subject';
        },
      });
  }

  onSubjectDetail(subjectId: number): void {
    this.router.navigateByUrl(`/mdd/subjects/${subjectId}`);
  }

  onAddSubjectForm(): void {
    this.router.navigateByUrl('/mdd/subjects/subject-form');
  }

  onEditSubject(id: string) {
    this.router.navigateByUrl(`/mdd/subjects/subject-form/${id}`);
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
