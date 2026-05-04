import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { ToastService } from '../../toast.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  form = { name: '', userName: '', email: '', phoneNo: '', password: '', city: '', state: '' };
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router, private toast: ToastService) { }

  onSignup(): void {
    if (!this.form.name || !this.form.userName || !this.form.email || !this.form.phoneNo || !this.form.password) {
      this.error = 'Please fill in all required fields';
      return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.form.email)) {
      this.error = 'Please enter a valid email address';
      return;
    }
    const phoneRegex = /^[0-9]{10}$/;
    if (!phoneRegex.test(this.form.phoneNo)) {
      this.error = 'Phone number must be exactly 10 digits';
      return;
    }
    if (this.form.password.length < 6) {
      this.error = 'Password must be at least 6 characters';
      return;
    }
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{6,}$/;
    if (!passwordRegex.test(this.form.password)) {
      this.error = 'Password must include uppercase, lowercase and number';
      return;
    }
    this.loading = true;
    this.error = '';

    this.authService.signup(this.form).subscribe({
      next: () => {
        this.loading = false;
        this.toast.success('Account created! Please sign in.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message || 'Registration failed. Please try again.';
      }
    });
  }

}
