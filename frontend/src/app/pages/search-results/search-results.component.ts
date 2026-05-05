import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ReviewResponse, TripResponse } from '../../models/bus.model';
import { AuthService } from '../../services/auth.service';
import { BusService } from '../../services/bus.service';
import { ReviewService } from '../../services/review.service';
import { IconComponent } from '../../shared/icon.component';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, IconComponent],
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css'],
})
export class SearchResultsComponent implements OnInit {
  src = '';
  dest = '';
  date = '';
  allTrips: TripResponse[] = [];
  filteredTrips: TripResponse[] = [];
  loading = false;

  busTypeFilter = 'ALL';
  minPrice: number | null = null;
  maxPrice: number | null = null;
  sortOrder = '';

  reviewsFor: number | null = null;
  reviews: ReviewResponse[] = [];
  reviewsLoading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private busService: BusService,
    private reviewService: ReviewService,
    public auth: AuthService,
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.src = params['src'] || '';
      this.dest = params['dest'] || '';
      this.date = params['date'] || '';
      if (this.src && this.dest && this.date) {
        this.loadTrips();
      }
    });
  }

  loadTrips(): void {
    this.loading = true;
    this.busService.searchTrips(this.src, this.dest, this.date).subscribe({
      next: (data) => {
        this.allTrips = data;
        this.applyFilters();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.allTrips = [];
        this.filteredTrips = [];
      },
    });
  }

  applyFilters(): void {
    let trips = [...this.allTrips];

    if (this.busTypeFilter !== 'ALL') {
      trips = trips.filter(
        (t) =>
          t.busType?.toUpperCase().replace(/[\s-]/g, '_') ===
          this.busTypeFilter,
      );
    }

    if (this.minPrice != null) {
      trips = trips.filter((t) => t.fare >= this.minPrice!);
    }
    if (this.maxPrice != null) {
      trips = trips.filter((t) => t.fare <= this.maxPrice!);
    }

    if (this.sortOrder === 'asc') {
      trips.sort((a, b) => a.fare - b.fare);
    } else if (this.sortOrder === 'desc') {
      trips.sort((a, b) => b.fare - a.fare);
    }

    this.filteredTrips = trips;
  }

  resetFilters(): void {
    this.busTypeFilter = 'ALL';
    this.minPrice = null;
    this.maxPrice = null;
    this.sortOrder = '';
    this.applyFilters();
  }

  toggleReviews(trip: TripResponse): void {
    if (this.reviewsFor === trip.tripId) {
      this.reviewsFor = null;
      return;
    }
    this.reviewsFor = trip.tripId;
    this.reviewsLoading = true;
    this.reviewService.getReviewsByTrip(trip.tripId).subscribe({
      next: (data) => {
        this.reviews = data;
        this.reviewsLoading = false;
      },
      error: () => {
        this.reviews = [];
        this.reviewsLoading = false;
      },
    });
  }

  formatTime(dt: string): string {
    if (!dt) return '';
    const d = new Date(dt);
    return d.toLocaleTimeString('en-IN', {
      hour: '2-digit',
      minute: '2-digit',
    });
  }
}
