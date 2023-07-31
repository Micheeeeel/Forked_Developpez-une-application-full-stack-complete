import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from '../../services/subject.service';

@Component({
  selector: 'app-subject-form',
  templateUrl: './SubjectForm.component.html',
  styleUrls: ['./SubjectForm.component.scss'],
})
export class SubjectFormComponent implements OnInit {
  subjectForm!: FormGroup;
  public isEdit = false; // public because we use for tests
  message: string | null = null;
  public subjectId: string | null = null;

  constructor(
    private formeBuilder: FormBuilder,
    private subjectService: SubjectService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.subjectForm = this.formeBuilder.group({
      name: [null, Validators.required],
    });

    // Get the subjectId from the route
    const subjectId = this.route.snapshot.paramMap.get('subjectId');
    if (subjectId) {
      this.isEdit = true;
      // Load the subject from the server and initialize the form
      this.subjectService.getSubjectById(subjectId).subscribe((subject) => {
        this.subjectForm.patchValue(subject);
      });
    }
  }

  onSubmitForm(): void {
    if (this.isEdit) {
      // Update the existing subject
      this.subjectId = this.route.snapshot.paramMap.get('id');
      if (!this.subjectId) {
        console.error('No subject id found in the route for editing');
        this.message = 'No subject id found in the route for editing';
        return;
      } else {
        this.subjectService
          .updateSubject(this.subjectId, this.subjectForm.value)
          .subscribe({
            next: (message) => {
              console.log(message); // "Subject updated successfully"
              this.message = 'Subject updated successfully';
              this.router.navigateByUrl('/subjects');
            },
            error: (error) => {
              console.error(error); // "Failed to update Subject"
            },
          });
      }
    } else {
      this.subjectService.addSubject(this.subjectForm.value).subscribe({
        next: (message) => {
          console.log(message); // "Subject created successfully"
          this.message = 'Subject created successfully';
          this.router.navigateByUrl('/subjects');
        },
        error: (error) => {
          console.error(error); // "Failed to create Subject"
          this.message = 'Failed to create Subject'; // <-- Add this line
        },
      });
    }
  }
}
