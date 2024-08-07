import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  isAuthenticated = false;
  isAdmin = false;
  isCreator = false;
  isConsumer = false;
  currentRoute = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      if (user) {
        this.isAuthenticated = true;
        this.isAdmin = user.userType === 'ADMIN';
        this.isCreator = user.userType === 'CREATOR';
        this.isConsumer = user.userType === 'CONSUMER';
      } else {
        this.isAuthenticated = false;
        this.isAdmin = false;
        this.isCreator = false;
        this.isConsumer = false;
      }
    });

    if (this.authService.isLoggedIn()) {
      this.authService.getUserProfile().subscribe(user => {
        this.authService.setCurrentUser(user);
      });
    }

    this.currentRoute = this.router.url;
  }

  setActiveRoute(route: string): void {
    this.currentRoute = route;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
