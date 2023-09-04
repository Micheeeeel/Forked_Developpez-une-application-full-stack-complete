import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, filter, map, takeUntil, tap } from 'rxjs';
import { Subject as MySubject } from 'src/app/core/models/Subject';
import { User } from 'src/app/core/models/User';
import { AuthService } from 'src/app/core/services/auth.service';
import { SessionService } from 'src/app/core/services/session.service';
import { SubjectService } from 'src/app/core/services/subject.service';
import { SubscriptionService } from 'src/app/core/services/subscriptionService';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit, OnDestroy {
  profileForm!: FormGroup;
  subjects: MySubject[] = [];
  errorMessage: string | null = null;
  currentUser!: User;
  message: string | null = null;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private subjectService: SubjectService,
    private subscriptionService: SubscriptionService,
    private sessionService: SessionService,
    private router: Router,
    private formeBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getUnsubscribedSubjects();

    this.currentUser = this.sessionService.user;

    this.profileForm = this.formeBuilder.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
    });
  }

  onSave() {
    if (this.profileForm.valid) {
      console.log(this.profileForm.value);
      this.authService
        .updateUser(this.currentUser.id.toString(), this.profileForm.value)
        .pipe()
        .subscribe({
          next: (message) => {
            this.handleSuccess('User updated successfully', message.token);
          },
          error: (error) => {
            this.handleError('Failed to update user');
          },
        });
    }
  }

  handleSuccess(message: string, token: string) {
    // Sauvegardez le token dans le localStorage
    localStorage.setItem('token', token);

    // Affichez le message
    console.log(message);
    this.message = message;

    // mémoriser les nouvelles données de l'utilisateur
    this.authService.getCurrentUser().subscribe((user: User) => {
      this.sessionService.logIn(user);
    });
  }

  handleError(message: string) {
    console.error(message);
    this.errorMessage = message;
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

    const userId: number = this.currentUser.id;

    if (subject.followed) {
      this.subscriptionService
        .unsubscribeSubject(subject.id, userId)
        .subscribe(() => {
          subject.followed = false;
        });
    } else {
      this.subscriptionService
        .subscribeToSubject(subject.id, userId)
        .subscribe(() => {
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
