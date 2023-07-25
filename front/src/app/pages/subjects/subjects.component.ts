import { Component, OnInit } from '@angular/core';
import { Subject } from 'src/app/models/Subject';
import { SubjectService } from '../../services/subject.service';
@Component({
  selector: 'app-subjects-component',
  templateUrl: './subjects.component.html',
  styleUrls: ['./subjects.component.scss'],
})
export class SubjectsComponent implements OnInit {
  subjects: Subject[] = [];
  errorMessage: string | null = null;

  constructor(private subjectService: SubjectService) {}

  ngOnInit(): void {
    this.getSubjects();
  }

  getSubjects(): void {
    this.subjectService.getSubjects().subscribe(
      (subjects) => {
        this.subjects = subjects;
      },
      (error) => {
        this.errorMessage = 'Error fetching subjects';
      }
    );
  }
}
