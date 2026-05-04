import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BookingResponse } from '../../models/bus.model';
import { BookingService } from '../../services/booking.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-my-bookings',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css']
})
export class MyBookingsComponent implements OnInit {
  allBookings: BookingResponse[] = [];
  loading = true;
  tab: 'upcoming' | 'past' = 'upcoming';

  constructor(private bookingService: BookingService, private toast: ToastService) {}

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings(): void {
    this.loading = true;
    this.bookingService.getMyBookings().subscribe({
      next: (data) => { this.allBookings = data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }

  get currentBookings(): BookingResponse[] {
    const today = new Date().toISOString().split('T')[0];
    if (this.tab === 'upcoming') {
      return this.allBookings.filter(b =>
        b.trip?.tripDate >= today && b.status !== 'CANCELLED'
      );
    } else {
      return this.allBookings.filter(b =>
        b.trip?.tripDate < today || b.status === 'CANCELLED'
      );
    }
  }

  cancelBooking(id: number): void {
    if (!confirm('Are you sure you want to cancel this booking?')) return;
    this.bookingService.cancelBooking(id).subscribe({
      next: () => {
        this.toast.success('Booking cancelled');
        this.loadBookings();
      },
      error: () => this.toast.error('Failed to cancel booking')
    });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'CONFIRMED': return 'badge-success';
      case 'CANCELLED': return 'badge-danger';
      case 'COMPLETED': return 'badge-info';
      default: return 'badge-warning';
    }
  }

  formatDate(dt: string): string {
    if (!dt) return '';
    return new Date(dt).toLocaleDateString('en-IN', { day: 'numeric', month: 'short', year: 'numeric' });
  }

  formatTime(dt: string | undefined): string {
    if (!dt) return '';
    return new Date(dt).toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit' });
  }
}
