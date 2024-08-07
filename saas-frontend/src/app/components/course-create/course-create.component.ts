import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-course-create',
  templateUrl: './course-create.component.html',
  styleUrls: ['./course-create.component.scss'],
})
export class CourseCreateComponent implements OnInit {
  courseForm: FormGroup = new FormGroup({});
  selectedFile: File | null = null;
  courses: Course[] = [];
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private courseService: CourseService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.courseForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: ['ACTIVE', Validators.required],
      imageUrl: ['']
    });

    // Load courses on initialization
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.getCourses().subscribe(
      (data: Course[]) => {
        this.courses = data;
      },
      error => console.error('Error loading courses', error)
    );
  }

  onFileSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.courseForm.valid) {
      const formData = new FormData();
      formData.append('title', this.courseForm.get('title')?.value);
      formData.append('description', this.courseForm.get('description')?.value);
      formData.append('status', this.courseForm.get('status')?.value);

      if (this.selectedFile) {
        formData.append('image', this.selectedFile, this.selectedFile.name);
      }

      const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('token'));

      this.http.post('http://localhost:8080/api/courses/create', formData, { headers })
        .subscribe(
          () => this.router.navigate(['/courses']),
          error => {
            this.errorMessage = error.error || 'An unknown error occurred';
          }
        );
    }
  }

  onCancel(): void {
    this.router.navigate(['/courses']);
  }
}
