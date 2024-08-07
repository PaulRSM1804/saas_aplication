import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCourseApprovalComponent } from './admin-course-approval.component';

describe('AdminCourseApprovalComponent', () => {
  let component: AdminCourseApprovalComponent;
  let fixture: ComponentFixture<AdminCourseApprovalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminCourseApprovalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminCourseApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
