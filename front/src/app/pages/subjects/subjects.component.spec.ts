import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubjectService } from '../../services/subject.service';
import { SubjectsComponent } from './subjects.component';
import { of } from 'rxjs/internal/observable/of';
import { HttpClientModule } from '@angular/common/http';
import { throwError } from 'rxjs';

describe('SubjectsComponent', () => {
  let component: SubjectsComponent;
  let fixture: ComponentFixture<SubjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectsComponent],
      imports: [HttpClientModule],
      providers: [SubjectService],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectsComponent);
    component = fixture.componentInstance;
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
    const subjectService = TestBed.inject(SubjectService);
    jest.spyOn(subjectService, 'getSubjects').mockReturnValue(of(subjects));

    // Déclencher le changement de détection pour mettre à jour la vue
    fixture.detectChanges();

    // Vérifier que les sujets sont affichés correctement dans le template
    const subjectElements =
      fixture.nativeElement.querySelectorAll('.subject-item');
    expect(subjectElements.length).toBe(3);
    expect(subjectElements[0].textContent).toContain('Subject 1');
    expect(subjectElements[1].textContent).toContain('Subject 2');
    expect(subjectElements[2].textContent).toContain('Subject 3');
  });

  it('should handle error when fetching subjects', () => {
    // Simuler une erreur lors de l'appel au service
    const subjectService = fixture.debugElement.injector.get(SubjectService);
    jest
      .spyOn(subjectService, 'getSubjects')
      .mockReturnValue(throwError('Error fetching subjects'));

    // Déclencher le changement de détection pour mettre à jour la vue
    fixture.detectChanges();

    // Vérifier que le message d'erreur est affiché dans le template
    const errorMessageElement =
      fixture.nativeElement.querySelector('.error-message');
    expect(errorMessageElement.textContent).toContain(
      'Error fetching subjects'
    );
  });
});
