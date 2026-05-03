import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="auth-page">
      <div class="auth-card fade-in">
        <div class="auth-header">
          <span class="auth-icon">🚌</span>
          <h1>Create an account</h1>
          <p>Start booking bus tickets with BusGo</p>
        </div>

        <form (ngSubmit)="onSignup()" class="auth-form">
          <div class="row g-3">
            <div class="col-12">
              <label class="form-label">Full Name *</label>
              <input type="text" class="form-input" [(ngModel)]="form.name" name="name" placeholder="John Doe" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Username *</label>
              <input type="text" class="form-input" [(ngModel)]="form.userName" name="userName" placeholder="johndoe" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Phone *</label>
              <input type="tel" class="form-input" [(ngModel)]="form.phoneNo" name="phoneNo" placeholder="9876543210" required>
            </div>
            <div class="col-12">
              <label class="form-label">Email *</label>
              <input type="email" class="form-input" [(ngModel)]="form.email" name="email" placeholder="john&#64;example.com" required>
            </div>
            <div class="col-12">
              <label class="form-label">Password *</label>
              <input type="password" class="form-input" [(ngModel)]="form.password" name="password" placeholder="Min 6 characters" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">City</label>
              <input type="text" class="form-input" [(ngModel)]="form.city" name="city" placeholder="Mumbai">
            </div>
            <div class="col-md-6">
              <label class="form-label">State</label>
              <input type="text" class="form-input" [(ngModel)]="form.state" name="state" placeholder="Maharashtra">
            </div>
          </div>

          <div *ngIf="error" class="error-msg">{{ error }}</div>

          <button type="submit" class="btn-primary-custom w-100" [disabled]="loading">
            <span *ngIf="loading" class="spinner"></span>
            {{ loading ? 'Creating account...' : 'Create Account' }}
          </button>
        </form>

        <p class="auth-footer">
          Already have an account? <a routerLink="/login">Sign in</a>
        </p>
      </div>
    </div>
  `,
  styles: [`
    .auth-page {
      min-height: calc(100vh - 70px);
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 40px 20px;
      background: var(--gray-50);
    }
    .auth-card {
      background: var(--white);
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-lg);
      padding: 40px;
      width: 100%;
      max-width: 520px;
      border: 1px solid var(--gray-200);
    }
    .auth-header { text-align: center; margin-bottom: 28px; }
    .auth-icon { font-size: 2.5rem; display: block; margin-bottom: 12px; }
    .auth-header h1 { font-size: 1.5rem; font-weight: 700; color: var(--dark); margin-bottom: 4px; }
    .auth-header p { font-size: 0.875rem; color: var(--gray-500); }
    .auth-form { display: flex; flex-direction: column; gap: 20px; }
    .w-100 { width: 100%; justify-content: center; padding: 12px !important; }
    .error-msg {
      background: #FEE2E2; color: #991B1B;
      padding: 10px 14px; border-radius: var(--radius-sm);
      font-size: 0.813rem; font-weight: 500;
    }
    .auth-footer { text-align: center; margin-top: 24px; font-size: 0.875rem; color: var(--gray-500); }
    .auth-footer a { color: var(--primary); font-weight: 600; }
  `]
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
