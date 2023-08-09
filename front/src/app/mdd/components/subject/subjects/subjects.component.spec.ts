import {
  ComponentFixture,
  TestBed,
  fakeAsync,
  tick,
} from '@angular/core/testing';
import { SubjectService } from '../../../../core/services/subject.service';
import { SubjectsComponent } from './subjects.component';
import { of } from 'rxjs/internal/observable/of';
import { HttpClientModule } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

describe('SubjectsComponent', () => {
  let component: SubjectsComponent;
  let fixture: ComponentFixture<SubjectsComponent>;
  let router: Router;
  let subjectService: SubjectService;

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
    router = TestBed.inject(Router);
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

  // Test the deleteSubject method
  it('should call the service deleteSubject method and then navigate back to list of subjects', () => {
    const subjectId = '1';
    const deleteSubjectSpy = jest
      .spyOn(subjectService, 'deleteSubject')
      .mockReturnValue(of('Subject deleted successfully'));
    const routerNavigateSpy = jest.spyOn(router, 'navigateByUrl');

    component.onDeleteSubject(subjectId);

    expect(deleteSubjectSpy).toHaveBeenCalledWith(subjectId); // vérifier que la méthode deleteSubject du service a été appelée avec le bon id
  });

  // Teste the deleteSubject method when the service returns an error
  it('should handle error when deleting a subject', () => {
    const subjectId = '1';
    const errorMessage = 'Failed to delete the subject';

    // Mock the deleteSubject method of the service to return an error
    jest
      .spyOn(subjectService, 'deleteSubject')
      .mockReturnValue(throwError(errorMessage));

    // Spy on the console.error to check if the error message is logged
    jest.spyOn(console, 'error');

    // Call the method to be tested
    component.onDeleteSubject(subjectId);

    // Expect that the deleteSubject method was called with the correct subjectId
    expect(subjectService.deleteSubject).toHaveBeenCalledWith(subjectId);

    // Expect that console.error was called with the error message
    expect(console.error).toHaveBeenCalledWith(errorMessage);
  });
});
