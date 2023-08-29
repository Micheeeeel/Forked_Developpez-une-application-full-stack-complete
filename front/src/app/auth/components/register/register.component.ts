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
import { User } from 'src/app/core/models/User';
import { SessionService } from 'src/app/core/services/session.service';
import { PasswordValidator } from 'src/app/shared/validators/password.validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit, AfterViewInit, OnDestroy {
  registerForm!: FormGroup;
  message: string | null = null;
  errorMessage: string | null = null;
  @ViewChild('usernameInput', { static: false }) usernameInput!: ElementRef;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private formeBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private session: SessionService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formeBuilder.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, PasswordValidator]],
    });
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => this.usernameInput.nativeElement.focus());
  }

  submit() {
    if (this.registerForm.valid) {
      console.log('ça envoie ce qui suit: ' + this.registerForm.value);

      this.auth
        .register(this.registerForm.value)
        .pipe()
        .subscribe({
          next: (message) => {
            this.handleSuccess('User created successfully', message.token);
          },
          error: (error) => {
            this.handleError('Failed to create user');
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
