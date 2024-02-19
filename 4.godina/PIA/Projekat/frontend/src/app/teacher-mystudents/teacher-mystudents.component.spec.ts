import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherMystudentsComponent } from './teacher-mystudents.component';

describe('TeacherMystudentsComponent', () => {
  let component: TeacherMystudentsComponent;
  let fixture: ComponentFixture<TeacherMystudentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherMystudentsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeacherMystudentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
