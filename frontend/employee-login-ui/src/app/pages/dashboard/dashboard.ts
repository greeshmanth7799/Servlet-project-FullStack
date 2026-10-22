
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {
  private auth = inject(AuthService);
  private router = inject(Router);

  name = '';
  email = '';
  message = 'Loading your account...';

  ngOnInit(): void {
    this.auth.getDashboard().subscribe({
      next: (response) => {
        if (response.user) {
          this.name = response.user.name;
          this.email = response.user.email;
          this.message = '';
        } else {
          this.router.navigate(['/login']);
        }
      },
      error: () => this.router.navigate(['/login'])
    });
  }

  logout(): void {
    this.auth.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => {
        this.message = 'Logout failed. Please try again.';
      }
    });
  }
}
