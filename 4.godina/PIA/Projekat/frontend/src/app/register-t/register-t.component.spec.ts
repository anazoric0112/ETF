import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterTComponent } from './register-t.component';

describe('RegisterTComponent', () => {
  let component: RegisterTComponent;
  let fixture: ComponentFixture<RegisterTComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterTComponent]
    });
    fixture = TestBed.createComponent(RegisterTComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
