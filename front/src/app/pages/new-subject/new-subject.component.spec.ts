import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NewSubjectComponent } from './new-subject.component';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { SubjectService } from '../../services/subject.service';

describe('NewSubjectComponent', () => {
  let component: NewSubjectComponent;
  let fixture: ComponentFixture<NewSubjectComponent>;
  let subjectService: SubjectService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NewSubjectComponent],
      imports: [HttpClientTestingModule, ReactiveFormsModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSubjectComponent);
    component = fixture.componentInstance;
    subjectService = TestBed.inject(SubjectService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form', () => {
    expect(component.subjectForm).toBeDefined();
    expect(component.subjectForm.get('name')).toBeDefined();
  });

  // Test the form validation
  it('should call addSubject when form is submitted with valid data', () => {
    const formData = { name: 'New Subject' }; // créer un objet formData avec un nom de sujet
    const addSubjectSpy = jest
      .spyOn(subjectService, 'addSubject')
      .mockReturnValue(of('Subject created successfully')); // simuler l'appel au service qui renvoie le message de succès
    const routerNavigateSpy = jest.spyOn(router, 'navigateByUrl'); // simuler l'appel à la méthode navigateByUrl du router

    component.subjectForm.setValue(formData);
    component.onSubmitForm();

    expect(addSubjectSpy).toHaveBeenCalledWith(formData);
    expect(routerNavigateSpy).toHaveBeenCalledWith('/subjects');
  });

  // Test the error handling
  it('should handle error when adding a subject', () => {
    const formData = { name: 'New Subject' };
    const errorMessage = 'Failed to create Subject';
    jest
      .spyOn(subjectService, 'addSubject')
      .mockReturnValue(throwError(errorMessage));

    // Create a mock console.error function
    const consoleErrorMock = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    component.subjectForm.setValue(formData);

    // Call the onSubmitForm method, which should trigger the error
    component.onSubmitForm();

    // The error handling code in the component's subscribe block should be called
    // and the console.error should log the error message
    expect(consoleErrorMock).toHaveBeenCalledWith(errorMessage);
  });

  // Test the form validation
  it('should disable the submit button if the form is invalid', () => {
    const submitButton = fixture.nativeElement.querySelector('[type="submit"]');

    // Initially, the form is invalid, so the submit button should be disabled
    expect(submitButton.disabled).toBe(true);

    // Set invalid values to the form fields
    component.subjectForm.controls['name'].setValue('');
    fixture.detectChanges();

    // The form is still invalid, so the submit button should remain disabled
    expect(submitButton.disabled).toBe(true);
  });

  // Test the form validation
  it('should enable the submit button if the form is valid', () => {
    const submitButton = fixture.nativeElement.querySelector('[type="submit"]');

    // Set valid values to the form fields
    component.subjectForm.controls['name'].setValue('Valid Subject');
    fixture.detectChanges();

    // The form is valid, so the submit button should be enabled
    expect(submitButton.disabled).toBe(false);
  });
});
