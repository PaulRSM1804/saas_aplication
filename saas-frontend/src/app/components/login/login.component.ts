import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login({ email: this.email, password: this.password }).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        this.router.navigate(['/courses']);
      },
      (error) => {
        console.error('Error during login:', error);
        alert('Login failed, please check your credentials');
      }
    );
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
