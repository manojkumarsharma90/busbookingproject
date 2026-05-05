import { Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  // Public Routes — eagerly loaded for instant navigation
  { path: '', loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent) },
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) },
  { path: 'signup', loadComponent: () => import('./pages/signup/signup.component').then(m => m.SignupComponent) },
  { path: 'search', loadComponent: () => import('./pages/search-results/search-results.component').then(m => m.SearchResultsComponent) },

  // User Protected Routes — lazy loaded
  { path: 'book/:tripId', loadComponent: () => import('./pages/book/book.component').then(m => m.BookComponent), canActivate: [authGuard] },
  { path: 'my-bookings', loadComponent: () => import('./pages/my-bookings/my-bookings.component').then(m => m.MyBookingsComponent), canActivate: [authGuard] },
  { path: 'my-reviews', loadComponent: () => import('./pages/my-reviews/my-reviews.component').then(m => m.MyReviewsComponent), canActivate: [authGuard] },

  // Admin Routes — lazy loaded
  { path: 'admin', loadComponent: () => import('./pages/admin/admin-dashboard/admin-dashboard.component').then(m => m.AdminDashboardComponent), canActivate: [adminGuard] },
  { path: 'admin/agencies', loadComponent: () => import('./pages/admin/admin-agencies/admin-agencies.component').then(m => m.AdminAgenciesComponent), canActivate: [adminGuard] },
  { path: 'admin/offices', loadComponent: () => import('./pages/admin/admin-offices/admin-offices.component').then(m => m.AdminOfficesComponent), canActivate: [adminGuard] },
  { path: 'admin/buses', loadComponent: () => import('./pages/admin/admin-buses/admin-buses.component').then(m => m.AdminBusesComponent), canActivate: [adminGuard] },
  { path: 'admin/drivers', loadComponent: () => import('./pages/admin/admin-drivers/admin-drivers.component').then(m => m.AdminDriversComponent), canActivate: [adminGuard] },
  { path: 'admin/routes', loadComponent: () => import('./pages/admin/admin-routes/admin-routes.component').then(m => m.AdminRoutesComponent), canActivate: [adminGuard] },
  { path: 'admin/trips', loadComponent: () => import('./pages/admin/admin-trips/admin-trips.component').then(m => m.AdminTripsComponent), canActivate: [adminGuard] },

  // Fallback
  { path: '**', redirectTo: '' }
];
