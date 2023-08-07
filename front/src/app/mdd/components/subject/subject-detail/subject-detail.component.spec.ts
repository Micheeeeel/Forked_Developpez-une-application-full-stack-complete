import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { SubjectDetailComponent } from './subject-detail.component';
import { RouterTestingModule } from '@angular/router/testing';
import { SubjectService } from '../../../../core/services/subject.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('SubjectDetailComponent', () => {
  let component: SubjectDetailComponent;
  let fixture: ComponentFixture<SubjectDetailComponent>;
  let subjectService: SubjectService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SubjectDetailComponent],
      imports: [RouterTestingModule, HttpClientTestingModule], // Add RouterTestingModule to mock the Router
      providers: [SubjectService], // Provide the SubjectService
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectDetailComponent);
    component = fixture.componentInstance;
    subjectService = TestBed.inject(SubjectService); // Inject the SubjectService
    router = TestBed.inject(Router); // Inject the Router
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch subject details from the service', () => {
    const subjectId = '123';
    const mockSubject = { id: 123, name: 'Subject 1' };
    jest
      .spyOn(subjectService, 'getSubjectById')
      .mockReturnValue(of(mockSubject)); // Mock the getSubjectById method to return an observable of mock subject

    // Trigger ngOnInit
    component.ngOnInit();

    // The subject$ observable should have the mock subject
    component.subject$.subscribe((subject) => {
      expect(subject).toEqual(mockSubject);
    });
  });

  // it('should navigate back when goBack method is called', () => {
  //   const routerSpy = jest.spyOn(router, 'navigate'); // Spy on the router.navigate method

  //   // Call the goBack method
  //   component.goBack();

  //   // Expect the router.navigate method to have been called with the expected URL
  //   expect(routerSpy).toHaveBeenCalledWith('/mdd/subjects');
  // });
});
