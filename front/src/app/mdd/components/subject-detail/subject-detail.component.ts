import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subject, catchError, throwError } from 'rxjs';
import { Subject as MySubject } from 'src/app/core/models/Subject';
import { SubjectService } from '../../../core/services/subject.service';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.scss'],
})
export class SubjectDetailComponent implements OnInit {
  public subjectId: string;
  public subject$!: Observable<MySubject>;
  errorMessage: string | null = null;

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

  private fetchSubject(): Observable<MySubject> {
    return this.subjectService.getSubjectById(this.subjectId).pipe(
      catchError((error) => {
        console.error(error);
        this.errorMessage = 'Error fetching subject';
        return throwError(() => new Error('Error fetching subject'));
      })
    );
  }

  goBack(): void {
    this.router.navigate(['/subjects']); // Navigate back to the list of subjects
  }
}
