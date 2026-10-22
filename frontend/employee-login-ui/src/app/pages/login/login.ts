
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  private auth = inject(AuthService);
  private router = inject(Router);

  email = '';
  password = '';
  message = '';
  messageType: 'success' | 'error' = 'error';
  loading = false;

  submit(): void {
    if (this.loading) return;

    this.loading = true;
    this.message = '';

    this.auth.login({
      email: this.email.trim(),
      password: this.password
    }).subscribe({
      next: (response) => {
        this.loading = false;

        if (response.user) {
          this.messageType = 'success';
          this.message = 'Login successful! Redirecting to dashboard...';

          setTimeout(() => {
            this.router.navigate(['/dashboard']);
          }, 1200);
        } else {
          this.messageType = 'error';
          this.message = response.message || 'Invalid email or password.';
        }
      },
      error: (error) => {
        this.loading = false;
        this.messageType = 'error';
        this.message =
          error.error?.message ?? 'Login failed. Check your credentials.';
      }
    });
  }
}
