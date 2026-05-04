import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BusDto, RouteResponse, TripResponse } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-trips',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-trips.component.html',
  styleUrls: ['./admin-trips.component.css']
})
export class AdminTripsComponent implements OnInit {
  trips: TripResponse[] = [];
  routes: RouteResponse[] = [];
  buses: BusDto[] = [];
  loading = true;

  showAddForm = false;
  // Payload for the entity expecting nested objects
  newTrip: any = {
    route: { routeId: 0 },
    bus: { busId: 0 },
    tripDate: '',
    departureTime: '',
    fare: 0,
    availableSeats: 40
  };

  editingId: number | null = null;
  editTripData: any = {};

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    // Load dependencies first
    this.adminService.getRoutes().subscribe(rData => {
      this.routes = rData;
      this.adminService.getBuses().subscribe(bData => {
        this.buses = bData;
        this.loadData();
      });
    });
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getTrips().subscribe({
      next: (data) => { this.trips = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load trips'); this.loading = false; }
    });
  }

  addTrip(): void {
    if (!this.newTrip.route.routeId || !this.newTrip.bus.busId || !this.newTrip.tripDate) {
      this.toast.error('Please fill required fields');
      return;
    }

    this.adminService.addTrip(this.newTrip).subscribe({
      next: () => {
        this.toast.success('Trip added successfully');
        this.newTrip = {
          route: { routeId: 0 },
          bus: { busId: 0 },
          tripDate: '',
          departureTime: '',
          fare: 0,
          availableSeats: 40
        };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add trip')
    });
  }

  editRow(trip: TripResponse): void {
    if (this.editingId === trip.tripId) return;
    this.editingId = trip.tripId;
    // Map response to expected update payload
    this.editTripData = {
      route: { routeId: trip.routeId },
      bus: { busId: trip.busId },
      tripDate: trip.tripDate,
      departureTime: trip.departureTime,
      fare: trip.fare,
      availableSeats: trip.availableSeats
    };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;

    this.adminService.updateTrip(this.editingId, this.editTripData).subscribe({
      next: () => {
        this.toast.success('Trip updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteTrip(id: number): void {
    if (!confirm('Delete this trip?')) return;
    this.adminService.deleteTrip(id).subscribe({
      next: () => {
        this.toast.success('Trip deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete')
    });
  }

  formatTime(dt: string): string {
    if (!dt) return '';
    return new Date(dt).toLocaleTimeString('en-IN', { hour: '2-digit', minute: '2-digit' });
  }
}
