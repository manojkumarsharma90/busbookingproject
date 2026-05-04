import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouteResponse } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-routes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-routes.component.html',
  styleUrls: ['./admin-routes.component.css']
})
export class AdminRoutesComponent implements OnInit {
  routes: RouteResponse[] = [];
  loading = true;

  showAddForm = false;
  newRoute: RouteResponse = { routeId: 0, fromCity: '', toCity: '', breakPoints: 0, duration: 60 };

  editingId: number | null = null;
  editRoute: RouteResponse = { routeId: 0, fromCity: '', toCity: '', breakPoints: 0, duration: 60 };

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getRoutes().subscribe({
      next: (data) => { this.routes = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load routes'); this.loading = false; }
    });
  }

  addRoute(): void {
    if (!this.newRoute.fromCity || !this.newRoute.toCity || !this.newRoute.duration) {
      this.toast.error('Please fill required fields');
      return;
    }
    // Route entity expects these directly for creation
    const routePayload = {
      fromCity: this.newRoute.fromCity,
      toCity: this.newRoute.toCity,
      breakPoints: this.newRoute.breakPoints,
      duration: this.newRoute.duration
    };

    this.adminService.addRoute(routePayload).subscribe({
      next: () => {
        this.toast.success('Route added successfully');
        this.newRoute = { routeId: 0, fromCity: '', toCity: '', breakPoints: 0, duration: 60 };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add route')
    });
  }

  editRow(route: RouteResponse): void {
    if (this.editingId === route.routeId) return;
    this.editingId = route.routeId;
    this.editRoute = { ...route };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;
    const routePayload = {
      fromCity: this.editRoute.fromCity,
      toCity: this.editRoute.toCity,
      breakPoints: this.editRoute.breakPoints,
      duration: this.editRoute.duration
    };

    this.adminService.updateRoute(this.editingId, routePayload).subscribe({
      next: () => {
        this.toast.success('Route updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteRoute(id: number): void {
    if (!confirm('Delete this route?')) return;
    this.adminService.deleteRoute(id).subscribe({
      next: () => {
        this.toast.success('Route deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete')
    });
  }
}
