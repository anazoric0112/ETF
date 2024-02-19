import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentProfileForOthersComponent } from './student-profile-for-others.component';

describe('StudentProfileForOthersComponent', () => {
  let component: StudentProfileForOthersComponent;
  let fixture: ComponentFixture<StudentProfileForOthersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StudentProfileForOthersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentProfileForOthersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
