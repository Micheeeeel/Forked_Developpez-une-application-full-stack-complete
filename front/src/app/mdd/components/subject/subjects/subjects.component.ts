import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject as MySubject } from 'src/app/core/models/Subject';
import { SubjectService } from '../../../../core/services/subject.service';
import { Observable, Subject, interval } from 'rxjs';
import { takeUntil, tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SubscriptionService } from 'src/app/core/services/subscriptionService';

@Component({
  selector: 'app-subjects-component',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit, OnDestroy {
  subjects: MySubject[] = [];
  errorMessage: string | null = null;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();
  isFollowed = false;

  constructor(
    private subjectService: SubjectService,
    private router: Router,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit(): void {
    this.getSubjects();
  }

  getSubjects(): void {
    this.subjectService
      .getSubjects()
      .pipe(
        tap({
          next: (subjects) => {
            this.subjects = subjects;
            console.log(subjects);
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

  onSubscribeSubject(subjectId: number) {
    const subject = this.subjects.find((s) => s.id === subjectId);
    if (!subject) return; // S'assurer que le sujet existe

    if (subject.followed) {
      this.subscriptionService.unsubscribeSubject(subjectId).subscribe(() => {
        subject.followed = false;
      });
    } else {
      this.subscriptionService.subscribeToSubject(subjectId).subscribe(() => {
        subject.followed = true;
      });
    }
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}