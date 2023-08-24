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
  @ViewChild('email', { static: false })
  email!: ElementRef;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private formeBuilder: FormBuilder,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formeBuilder.group({
      email: [null, [Validators.required]],
      password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => this.email.nativeElement.focus());
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

    // Redirigez vers la page désirée
    this.router.navigateByUrl('/mdd/article');
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
