import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgencyOfficeResponse, BusDto } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-buses',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-buses.component.html',
  styleUrls: ['./admin-buses.component.css']
})
export class AdminBusesComponent implements OnInit {
  buses: BusDto[] = [];
  offices: AgencyOfficeResponse[] = [];
  loading = true;

  showAddForm = false;
  newBus: BusDto = { busId: 0, registrationNumber: '', type: 'AC', capacity: 40, officeId: 0 };

  editingId: number | null = null;
  editBus: BusDto = { busId: 0, registrationNumber: '', type: 'AC', capacity: 40, officeId: 0 };

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    this.adminService.getOffices().subscribe({
      next: (data) => {
        this.offices = data;
        this.loadData();
      },
      error: () => this.loadData() // try loading anyway
    });
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getBuses().subscribe({
      next: (data) => { this.buses = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load buses'); this.loading = false; }
    });
  }

  addBus(): void {
    if (!this.newBus.registrationNumber || !this.newBus.officeId) {
      this.toast.error('Please fill required fields');
      return;
    }
    this.adminService.addBus(this.newBus).subscribe({
      next: () => {
        this.toast.success('Bus added successfully');
        this.newBus = { busId: 0, registrationNumber: '', type: 'AC', capacity: 40, officeId: 0 };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add bus')
    });
  }

  editRow(bus: BusDto): void {
    if (this.editingId === bus.busId) return;
    this.editingId = bus.busId;
    this.editBus = { ...bus };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;
    this.adminService.updateBus(this.editingId, this.editBus).subscribe({
      next: () => {
        this.toast.success('Bus updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteBus(id: number): void {
    if (!confirm('Delete this bus?')) return;
    this.adminService.deleteBus(id).subscribe({
      next: () => {
        this.toast.success('Bus deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete')
    });
  }
}
