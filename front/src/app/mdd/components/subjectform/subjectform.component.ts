import {
  AfterViewInit,
  Component,
  ElementRef,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from '../../../core/services/subject.service';
import { Subject } from 'rxjs/internal/Subject';
import { takeUntil } from 'rxjs/internal/operators/takeUntil';

@Component({
  selector: 'app-subject-form',
  templateUrl: './SubjectForm.component.html',
  styleUrls: ['./SubjectForm.component.scss'],
})
export class SubjectFormComponent implements OnInit, AfterViewInit, OnDestroy {
  subjectForm!: FormGroup;
  isEdit: boolean = false;
  message: string | null = null;
  public subjectId: string | null = null;
  @ViewChild('nameInput', { static: false }) nameInput!: ElementRef;
  errorMessage: string | null = null;

  private unsubscribe$: Subject<boolean> = new Subject<boolean>();

  constructor(
    private formeBuilder: FormBuilder,
    private subjectService: SubjectService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.subjectForm = this.formeBuilder.group({
      name: [null, Validators.required],
    });

    // Get the subjectId from the route
    this.subjectId = this.route.snapshot.paramMap.get('id');
    if (this.subjectId) {
      // Load the subject from the server and initialize the form
      this.subjectService
        .getSubjectById(this.subjectId)
        .subscribe((subject) => {
          this.subjectForm.patchValue(subject);
          // Set isEdit to true here
          this.isEdit = true;
        });
    }
  }

  ngAfterViewInit(): void {
    Promise.resolve().then(() => this.nameInput.nativeElement.focus());
  }

  getSubjectIdFromRoute(): string | null {
    return this.route.snapshot.paramMap.get('id');
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

  updateSubject(id: string | null) {
    if (!id) {
      this.handleError('No subject id found in the route for editing');
      return;
    }
    this.subjectService
      .updateSubject(id, this.subjectForm.value)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (message) => {
          console.log(message); // "Subject updated successfully"
          this.handleSuccess('Subject updated successfully');
        },
        error: (error) => {
          this.handleError('Failed to update Subject');
        },
      });
  }

  createSubject() {
    this.subjectService
      .addSubject(this.subjectForm.value)
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe({
        next: (message) => {
          console.log(message); // "Subject created successfully"
          this.handleSuccess('Subject created successfully');
        },
        error: (error) => {
          this.handleError('Failed to create Subject');
        },
      });
  }

  onSubmitForm(): void {
    if (this.isEdit) {
      this.updateSubject(this.getSubjectIdFromRoute());
    } else {
      this.createSubject();
    }
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next(true);

    // Unsubscribe from the subjectService
    this.unsubscribe$.unsubscribe();
  }
}
