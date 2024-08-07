import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-admin-course-approval',
  templateUrl: './admin-course-approval.component.html',
  styleUrls: ['./admin-course-approval.component.scss']
})
export class AdminCourseApprovalComponent implements OnInit {
  pendingCourses: Course[] = [];

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.loadPendingCourses();
  }

  loadPendingCourses(): void {
    this.courseService.getPendingCourses().subscribe((courses: Course[]) => {
      this.pendingCourses = courses.map(course => {
        course.imageUrl = `http://localhost:8080${course.imageUrl}`;
        return course;
      });
    });
  }

  approveCourse(courseId: number): void {
    this.courseService.approveCourse(courseId).subscribe(() => {
      this.loadPendingCourses(); // Refresh the list after approval
    }, error => {
      console.error('Error al aprobar el curso', error);
    });
  }

  rejectCourse(courseId: number): void {
    this.courseService.rejectCourse(courseId).subscribe(() => {
      this.loadPendingCourses(); // Refresh the list after rejection
    }, error => {
      console.error('Error al rechazar el curso', error);
    });
  }
}
