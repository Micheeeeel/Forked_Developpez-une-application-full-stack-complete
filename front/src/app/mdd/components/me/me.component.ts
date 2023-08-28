import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, filter, map, takeUntil, tap } from 'rxjs';
import { Subject as MySubject } from 'src/app/core/models/Subject';
import { User } from 'src/app/core/models/User';
import { SessionService } from 'src/app/core/services/session.service';
import { SubjectService } from 'src/app/core/services/subject.service';
import { SubscriptionService } from 'src/app/core/services/subscriptionService';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit, OnDestroy {
  subjects: MySubject[] = [];
  errorMessage: string | null = null;
  currentUser!: User;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private subjectService: SubjectService,
    private subscriptionService: SubscriptionService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getUnsubscribedSubjects();

    this.currentUser = this.sessionService.user;
  }

  getUnsubscribedSubjects(): void {
    this.subjectService
      .getSubjects()
      .pipe(
        map((subjects) => subjects.filter((subject) => subject.followed)),
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

  onSubscribeSubject(subject: MySubject) {
    if (!subject) return; // S'assurer que le sujet existe

    if (subject.followed) {
      this.subscriptionService.unsubscribeSubject(subject.id).subscribe(() => {
        subject.followed = false;
      });
    } else {
      this.subscriptionService.subscribeToSubject(subject.id).subscribe(() => {
        subject.followed = true;
      });
    }
  }

  onLogoutClick() {
    this.sessionService.logOut();

    // Redirigez vers la page d'accueil
    this.router.navigateByUrl('/');
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
