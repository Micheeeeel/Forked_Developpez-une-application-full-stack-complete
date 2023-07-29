import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SubjectService } from 'src/app/services/subject.service';

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
    private router: Router
  ) {}

  ngOnInit(): void {
    this.subjectForm = this.formeBuilder.group({
      name: [null, Validators.required],
    });
  }

  onSubmitForm(): void {
    this.subjectService.addSubject(this.subjectForm.value);
    this.router.navigateByUrl('/subjects');
  }
}
