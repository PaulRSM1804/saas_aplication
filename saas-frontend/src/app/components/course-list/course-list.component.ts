import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';import { SubscriptionService } from '../../services/subscription.service';

import { AuthService } from '../../services/auth.service';
import { Course } from '../../models/course.model';
import { User } from '../../models/user.model';
import { Subscription } from '../../models/subscription.model';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent implements OnInit {
  courses: Course[] = [];
  currentUser: User | null = null;
  consumerId: number | null = null;
  errorMessage: string | null = null;
  subscriptions: Subscription[] = [];



  constructor(
    private courseService: CourseService,
    private subscriptionService: SubscriptionService,

    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.getCurrentUser();
  }

  getCurrentUser(): void {
    this.authService.getUserProfile().subscribe(user => {
      this.currentUser = user;
      console.log(this.currentUser); // Verificar el usuario actual en la consola
      if (user.userType === 'CONSUMER') {
        this.consumerId = user.id;
      }
      this.loadCourses(); 
    });
  }

  loadCourses(): void {
    if (this.currentUser && this.currentUser.userType === 'CREATOR') {
      const creatorId = this.currentUser.id;
      this.courseService.getCoursesByCreator(creatorId).subscribe((courses: Course[]) => {
        this.courses = courses.map(course => {
          course.imageUrl = `http://localhost:8080${course.imageUrl}`;
          return course;
        });
      }, error => {
        console.error('Error al cargar los cursos del creador', error);
      });
    } else {
    this.courseService.getCourses().subscribe((courses: Course[]) => {
      this.courses = courses.filter(course => course.approvalStatus === 'APPROVED').map(course => {
        course.imageUrl = `http://localhost:8080${course.imageUrl}`;
        return course;

      }
      );
    }
    );
  }
}


subscribe(courseId: number): void {
  this.errorMessage = null; // Reset error message
  if (this.consumerId) {
    this.subscriptionService.createSubscription(this.consumerId, courseId).subscribe(() => {
      alert('Suscripción exitosa');
    }, error => {
      this.errorMessage = error.error.message || 'Error al suscribirse solo puede acceder a un curso por PROFESOR!!';
      console.error('Error al suscribirse solo puede acceder a un curso por PROFESOR!!', error);
    });
  } else {
    alert('Debe estar logeado como CONSUMER para suscribirse');
  }
}

isSubscribed(courseId: number): boolean {
  return this.subscriptions.some(sub => sub.courseId === courseId);
}

deleteCourse(courseId: number): void {
  this.courseService.deleteCourse(courseId).subscribe(() => {
    this.courses = this.courses.filter(course => course.id !== courseId);
    alert('Curso eliminado exitosamente');
  }, error => {
    this.errorMessage = error.message || 'Error al eliminar el curso no se puede eliminar un curso con usuario suscrito';
    console.log('Error al eliminar el curso no se puede eliminar un curso con usuario suscrito', error);
  });
}


}
