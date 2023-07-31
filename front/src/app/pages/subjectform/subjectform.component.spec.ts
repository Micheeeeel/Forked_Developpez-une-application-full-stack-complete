import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubjectFormComponent } from './subjectform.component';
import { of, throwError } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { SubjectService } from '../../services/subject.service';

describe('SubjectFormComponent', () => {
  let component: SubjectFormComponent;
  let fixture: ComponentFixture<SubjectFormComponent>;
  let subjectService: SubjectService;
  let router: Router;
  let activatedRoute: ActivatedRoute;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectFormComponent],
      imports: [HttpClientTestingModule, ReactiveFormsModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: { get: jest.fn() } },
          },
        },
        // other providers
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectFormComponent);
    component = fixture.componentInstance;
    subjectService = TestBed.inject(SubjectService);
    router = TestBed.inject(Router);
    activatedRoute = TestBed.inject(ActivatedRoute);
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

  // Test updateSubject when form is submitted with valid data in edit mode
  it('should call updateSubject when form is submitted with valid data in edit mode', () => {
    const formData = { name: 'Updated Subject' };
    const subjectId = '1'; // You can specify any value that makes sense for your tests
    const message = 'Subject updated successfully';

    const updateSubjectSpy = jest
      .spyOn(subjectService, 'updateSubject')
      .mockReturnValue(of(message));

    const routerNavigateSpy = jest.spyOn(router, 'navigateByUrl');
    const consoleErrorMock = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    // Simulate the behavior of ActivatedRoute
    jest
      .spyOn(activatedRoute.snapshot.paramMap, 'get')
      .mockReturnValue(subjectId);

    component.isEdit = true;
    component.subjectForm.setValue(formData);
    component.onSubmitForm();

    expect(updateSubjectSpy).toHaveBeenCalledWith(subjectId, formData);
    expect(routerNavigateSpy).toHaveBeenCalledWith('/subjects');
  });

  // Test error handling when updating a subject
  it('should handle error when updating a subject', () => {
    const formData = { name: 'Updated Subject' };
    const subjectId = '1'; // You can specify any value that makes sense for your tests
    const message = 'Failed to update Subject';

    jest
      .spyOn(subjectService, 'updateSubject')
      .mockReturnValue(throwError(message));

    // Simulate the behavior of ActivatedRoute
    jest
      .spyOn(activatedRoute.snapshot.paramMap, 'get')
      .mockReturnValue(subjectId);

    // Create a mock console.error function
    const consoleErrorMock = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    component.isEdit = true;
    component.subjectForm.setValue(formData);
    component.onSubmitForm(); // should trigger the error

    // The error handling code in the component's subscribe block should be called
    // and the console.error should log the error message
    expect(consoleErrorMock).toHaveBeenCalledWith(message);
  });
});
