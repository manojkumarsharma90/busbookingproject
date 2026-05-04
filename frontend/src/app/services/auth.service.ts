import { Injectable } from '@angular/core';

import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Route, Router } from '@angular/router';
import { environment } from '../enviroment';
import { AuthRequest, LoginResponse, SignupRequest } from '../models/auth';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  login(request: AuthRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, request).pipe(
      tap((res) => {
        localStorage.setItem('token', res.token);
        localStorage.setItem('username', res.userName);
        localStorage.setItem('role', res.role);
      }),
    );
  }

  signup(request: SignupRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/signup`, request);
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }
}
