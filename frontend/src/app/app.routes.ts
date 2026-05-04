import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { SearchResultsComponent } from './pages/search-results/search-results.component';
import { BookComponent } from './pages/book/book.component';
import { MyBookingsComponent } from './pages/my-bookings/my-bookings.component';
import { MyReviewsComponent } from './pages/my-reviews/my-reviews.component';

import { AdminDashboardComponent } from './pages/admin/admin-dashboard/admin-dashboard.component';
import { AdminAgenciesComponent } from './pages/admin/admin-agencies/admin-agencies.component';
import { AdminOfficesComponent } from './pages/admin/admin-offices/admin-offices.component';
import { AdminBusesComponent } from './pages/admin/admin-buses/admin-buses.component';
import { AdminDriversComponent } from './pages/admin/admin-drivers/admin-drivers.component';
import { AdminRoutesComponent } from './pages/admin/admin-routes/admin-routes.component';
import { AdminTripsComponent } from './pages/admin/admin-trips/admin-trips.component';

import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  // Public Routes
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'search', component: SearchResultsComponent },

  // User Protected Routes
  { path: 'book/:tripId', component: BookComponent, canActivate: [authGuard] },
  { path: 'my-bookings', component: MyBookingsComponent, canActivate: [authGuard] },
  { path: 'my-reviews', component: MyReviewsComponent, canActivate: [authGuard] },

  // Admin Routes
  { path: 'admin', component: AdminDashboardComponent, canActivate: [adminGuard] },
  { path: 'admin/agencies', component: AdminAgenciesComponent, canActivate: [adminGuard] },
  { path: 'admin/offices', component: AdminOfficesComponent, canActivate: [adminGuard] },
  { path: 'admin/buses', component: AdminBusesComponent, canActivate: [adminGuard] },
  { path: 'admin/drivers', component: AdminDriversComponent, canActivate: [adminGuard] },
  { path: 'admin/routes', component: AdminRoutesComponent, canActivate: [adminGuard] },
  { path: 'admin/trips', component: AdminTripsComponent, canActivate: [adminGuard] },

  // Fallback
  { path: '**', redirectTo: '' }
];
