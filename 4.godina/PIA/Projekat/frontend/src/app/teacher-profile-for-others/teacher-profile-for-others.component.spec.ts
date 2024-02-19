import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherProfileForOthersComponent } from './teacher-profile-for-others.component';

describe('TeacherProfileForOthersComponent', () => {
  let component: TeacherProfileForOthersComponent;
  let fixture: ComponentFixture<TeacherProfileForOthersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeacherProfileForOthersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TeacherProfileForOthersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
