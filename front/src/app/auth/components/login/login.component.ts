import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { takeUntil } from 'rxjs/internal/operators/takeUntil';
import { Subject } from 'rxjs';
import { SessionService } from 'src/app/core/services/session.service';
import { User } from 'src/app/core/models/User';
import { PasswordValidator } from 'src/app/shared/validators/password.validator';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, AfterViewInit, OnDestroy {
  loginForm!: FormGroup;
  message: string | null = null;
  errorMessage: string | null = null;
  @ViewChild('email', { static: false })
  login!: ElementRef;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private formeBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private session: SessionService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formeBuilder.group({
      login: [null, [Validators.required]],
      password: [null, [Validators.required, PasswordValidator]],
    });
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => this.login.nativeElement.focus());
  }

  submit() {
    if (this.loginForm.valid) {
      this.auth
        .login(this.loginForm.value)
        .pipe()
        .subscribe({
          next: (message) => {
            this.handleSuccess('User logged successfully', message.token);
          },
          error: (error) => {
            this.handleError('Failed to log user');
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

    // mémoriser l'utilisateur
    this.auth.getCurrentUser().subscribe((user: User) => {
      this.session.logIn(user);

      // Redirigez vers la page désirée
      this.router.navigateByUrl('/mdd/article');
    });
  }

  handleError(message: string) {
    console.error(message);
    this.errorMessage = message;
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
