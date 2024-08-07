import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  settingsForm: FormGroup;
  user: any;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.settingsForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      firstName: ['', [Validators.required]],
      lastName: ['']
    });
  }

  ngOnInit(): void {
    this.authService.getUserProfile().subscribe(user => {
      this.user = user;
      this.settingsForm.patchValue(user);
    });
  }

  saveSettings() {
    if (this.settingsForm.valid) {
      this.authService.updateUserProfile(this.settingsForm.value).subscribe(
        () => {
          alert('Profile updated successfully');
          this.router.navigate(['/courses']);
        },
        error => {
          console.error('Error updating profile', error);
          alert('Failed to update profile');
        }
      );
    }
  }
}
