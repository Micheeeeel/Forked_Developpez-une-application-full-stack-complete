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

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit, AfterViewInit, OnDestroy {
  loginForm!: FormGroup;
  message: string | null = null;
  errorMessage: string | null = null;
  @ViewChild('usernameInput', { static: false }) usernameInput!: ElementRef;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private formeBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formeBuilder.group({
      username: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => this.usernameInput.nativeElement.focus());
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      // Appelez votre méthode de connexion ici, peut-être avec email et password
      this.auth.login();

      // Redirigez vers la page désirée
      this.router.navigateByUrl('/mdd/subjects');
    }
  }

  createUser() {
    if (this.loginForm.valid) {
      this.auth
        .createUser(this.loginForm.value)
        .pipe()
        .subscribe({
          next: (message) => {
            console.log(message); // "Subject created successfully"
            this.handleSuccess('User created successfully');
          },
          error: (error) => {
            console.error(error);
            this.handleError('Failed to create user');
          },
        });
    }
  }

  handleSuccess(message: string) {
    console.log(message);
    this.message = message;
    this.router.navigateByUrl('/mdd/subjects');
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
