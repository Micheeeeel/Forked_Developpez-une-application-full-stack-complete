import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Subject } from 'src/app/models/Subject';
import { SubjectService } from '../../../services/subject.service';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.scss'],
})
export class SubjectDetailComponent implements OnInit {
  public subjectId: string;
  public subject$!: Observable<Subject>;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private subjectService: SubjectService
  ) {
    this.subjectId = this.route.snapshot.paramMap.get('id')!;
  }

  ngOnInit(): void {
    this.subject$ = this.fetchSubject();
  }

  private fetchSubject(): Observable<Subject> {
    return this.subjectService.getSubjectById(this.subjectId);
  }

  goBack(): void {
    this.router.navigate(['/subjects']); // Navigate back to the list of subjects
  }
}
