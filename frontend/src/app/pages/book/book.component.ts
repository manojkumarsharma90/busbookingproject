import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PassengerDto, TripResponse } from '../../models/bus.model';
import { AuthService } from '../../services/auth.service';
import { BookingService } from '../../services/booking.service';
import { BusService } from '../../services/bus.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {
  tripId!: number;
  trip: TripResponse | null = null;
  loading = true;
  bookedSeats: string[] = [];
  allSeats: string[] = [];
  selectedSeats: string[] = [];
  passengers: PassengerDto[] = [];
  bookingLoading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private busService: BusService,
    private bookingService: BookingService,
    private toast: ToastService,
    public auth: AuthService
  ) { }

  ngOnInit(): void {
    this.tripId = +this.route.snapshot.paramMap.get('tripId')!;
    this.loadTrip();
  }

  loadTrip(): void {
    this.busService.getTripById(this.tripId).subscribe({
      next: (trip) => {
        this.trip = trip;
        this.loadBookedSeats(); // Load booked seats first
      },
      error: () => { this.loading = false; }
    });
  }

  loadBookedSeats(): void {
    this.busService.getBookedSeats(this.tripId).subscribe({
      next: (seats) => {
        this.bookedSeats = seats;
        // Total capacity remains constant: available seats + already booked seats
        const totalCapacity = this.trip!.availableSeats + this.bookedSeats.length;
        this.generateSeats(totalCapacity);
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  generateSeats(capacity: number): void {
    this.allSeats = [];
    const rows = Math.ceil(capacity / 5);
    for (let r = 1; r <= rows; r++) {
      for (let c = 1; c <= 5; c++) {
        const num = (r - 1) * 5 + c;
        if (num <= capacity) {
          this.allSeats.push(`S${num}`);
        }
      }
    }
  }

  isBooked(seat: string): boolean {
    return this.bookedSeats.includes(seat);
  }

  isSelected(seat: string): boolean {
    return this.selectedSeats.includes(seat);
  }

  toggleSeat(seat: string): void {
    if (this.isBooked(seat)) return;
    const idx = this.selectedSeats.indexOf(seat);
    if (idx >= 0) {
      this.selectedSeats.splice(idx, 1);
      this.passengers = this.passengers.filter(p => p.seatNo !== seat);
    } else {
      this.selectedSeats.push(seat);
      this.passengers.push({ name: '', age: 0, seatNo: seat, gender: '' });
    }
  }

  onBook(): void {
    for (const p of this.passengers) {
      if (!p.name || !p.age || !p.gender) {
        this.error = 'Please fill in all passenger details';
        return;
      }
      if (p.age < 1 || p.age > 80) {
        this.error = `Invalid age for passenger ${p.name || 'at seat ' + p.seatNo}. Must be between 1 and 80.`;
        return;
      }
    }
    this.error = '';
    this.bookingLoading = true;

    if (this.auth.isAdmin()) {
      this.error = 'Admin accounts cannot book tickets. Please log in as a customer.';
      return;
    }

    this.bookingService.bookBus({ tripId: this.tripId, passenger: this.passengers }).subscribe({
      next: () => {
        this.bookingLoading = false;
        this.toast.success('Booking confirmed!');
        this.router.navigate(['/my-bookings']);
      },
      error: (err) => {
        console.error('Booking failed:', err);
        this.bookingLoading = false;
        this.error = err?.error?.errormsg || err?.error?.message || 'Booking failed. Please try again.';
      }
    });
  }

  formatTime(dt: string): string {
    if (!dt) return '';
    return new Date(dt).toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit' });
  }
}
