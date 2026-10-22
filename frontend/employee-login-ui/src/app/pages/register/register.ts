
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  private auth = inject(AuthService);
  private router = inject(Router);

  name = '';
  email = '';
  password = '';
  message = '';
  messageType: 'success' | 'error' = 'error';
  loading = false;

  submit(): void {
  if (this.loading) return;

  this.loading = true;
  this.message = '';

  console.time('Registration API time');

  this.auth.register({
    name: this.name.trim(),
    email: this.email.trim(),
    password: this.password
  }).subscribe({
    next: (response) => {
      console.timeEnd('Registration API time');

      this.loading = false;
      this.messageType = 'success';
      this.message = 'Registration successful! You can now log in.';

      console.log('Registration response:', response);
    },

    error: (error) => {
      console.timeEnd('Registration API time');

      this.loading = false;
      this.messageType = 'error';
      this.message =
        error.error?.message ?? 'Registration failed. Please try again.';
    }
  });
}
  
}