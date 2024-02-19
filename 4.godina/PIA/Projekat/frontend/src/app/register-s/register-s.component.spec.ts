import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterSComponent } from './register-s.component';

describe('RegisterSComponent', () => {
  let component: RegisterSComponent;
  let fixture: ComponentFixture<RegisterSComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterSComponent]
    });
    fixture = TestBed.createComponent(RegisterSComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
