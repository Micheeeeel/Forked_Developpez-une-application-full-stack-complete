import { Component, NgZone, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SubjectService } from '../../services/subject.service';

@Component({
  selector: 'app-new-subject',
  templateUrl: './new-subject.component.html',
  styleUrls: ['./new-subject.component.scss'],
})
export class NewSubjectComponent implements OnInit {
  subjectForm!: FormGroup;

  constructor(
    private formeBuilder: FormBuilder,
    private subjectService: SubjectService,
    private router: Router,
    private ngZone: NgZone
  ) {}

  ngOnInit(): void {
    this.subjectForm = this.formeBuilder.group({
      name: [null, Validators.required],
    });
  }

  onSubmitForm(): void {
    this.subjectService.addSubject(this.subjectForm.value).subscribe({
      next: (message) => {
        console.log(message); // "Subject created successfully"
        this.ngZone.run(() => {
          this.router.navigateByUrl('/subjects');
        });
      },
      error: (error) => {
        console.error(error); // "Failed to create Subject"
      },
    });
  }
}
