import {
  AfterViewInit,
  Component,
  ElementRef,
  NgZone,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from '../../services/subject.service';

@Component({
  selector: 'app-subject-form',
  templateUrl: './SubjectForm.component.html',
  styleUrls: ['./SubjectForm.component.scss'],
})
export class SubjectFormComponent implements AfterViewInit {
  subjectForm!: FormGroup;
  isEdit: boolean = false;
  message: string | null = null;
  public subjectId: string | null = null;
  @ViewChild('nameInput', { static: false }) nameInput!: ElementRef;

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

  handleError(message: string) {
    console.error(message);
    this.message = message;
  }

  handleSuccess(message: string) {
    console.log(message);
    this.message = message;
    this.router.navigateByUrl('/subjects');
  }

  updateSubject(id: string | null) {
    if (!id) {
      this.handleError('No subject id found in the route for editing');
      return;
    }
    this.subjectService.updateSubject(id, this.subjectForm.value).subscribe({
      next: (message) => this.handleSuccess('Subject updated successfully'),
      error: (error) => this.handleError('Failed to update Subject'),
    });
  }

  createSubject() {
    this.subjectService.addSubject(this.subjectForm.value).subscribe({
      next: (message) => this.handleSuccess('Subject created successfully'),
      error: (error) => this.handleError('Failed to create Subject'),
    });
  }

  onSubmitForm(): void {
    if (this.isEdit) {
      this.updateSubject(this.getSubjectIdFromRoute());
    } else {
      this.createSubject();
    }
  }
}
