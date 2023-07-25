import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectsComponent } from './subjects.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { expect } from '@jest/globals';
import { SubjectService } from 'src/app/services/subject.service';

describe('SubjectsComponent', () => {
  let component: SubjectsComponent;
  let fixture: ComponentFixture<SubjectsComponent>;
  let subjectService: SubjectService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectsComponent],
      providers: [SubjectService], // Add the SubjectService provider
      imports: [HttpClientTestingModule], // Add the HttpClientTestingModule
    }).compileComponents();

    fixture = TestBed.createComponent(SubjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    subjectService = TestBed.inject(SubjectService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display subjects', () => {
    const subjects = [
      { id: 1, name: 'Subject 1' },
      { id: 2, name: 'Subject 2' },
      { id: 3, name: 'Subject 3' },
    ];

    // Simuler l'appel au service qui renvoie la liste des sujets
    const spy = jest
      .spyOn(subjectService, 'getSubjects')
      .mockReturnValue(of(subjects));

    // Déclencher le cycle de vie du composant
    fixture.detectChanges();

    // Vérifier que les sujets affichés correctement dans le template
    const compiled = fixture.nativeElement.querySelectorAll('.subject-item'); // récupérer tous les éléments du DOM qui ont la classe .subject-item
    expect(compiled.length).toBe(3); // vérifier qu'il y a 3 éléments
    expect(compiled[0].textContent).toContain('Subject 1'); // vérifier que le premier élément contient le texte 'Subject 1'
    expect(compiled[1].textContent).toContain('Subject 2'); // vérifier que le deuxième élément contient le texte 'Subject 2'
    expect(compiled[2].textContent).toContain('Subject 3'); // vérifier que le troisième élément contient le texte 'Subject 3'
  });
});
