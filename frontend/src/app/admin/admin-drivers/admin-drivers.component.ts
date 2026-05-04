import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgencyOfficeResponse, DriverDto } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-drivers',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-drivers.component.html',
  styleUrls: ['./admin-drivers.component.css']
})
export class AdminDriversComponent implements OnInit {
  drivers: DriverDto[] = [];
  offices: AgencyOfficeResponse[] = [];
  loading = true;

  showAddForm = false;
  newDriver: DriverDto = { driverId: 0, name: '', licenseNumber: '', phone: '', officeId: 0, addressId: 0 };

  editingId: number | null = null;
  editDriver: DriverDto = { driverId: 0, name: '', licenseNumber: '', phone: '', officeId: 0, addressId: 0 };

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    this.adminService.getOffices().subscribe({
      next: (data) => {
        this.offices = data;
        this.loadData();
      },
      error: () => this.loadData()
    });
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getDrivers().subscribe({
      next: (data) => { this.drivers = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load drivers'); this.loading = false; }
    });
  }

  addDriver(): void {
    if (!this.newDriver.name || !this.newDriver.licenseNumber || !this.newDriver.officeId) {
      this.toast.error('Please fill required fields');
      return;
    }

    const payload = { ...this.newDriver };
    if (payload.addressId === 0) payload.addressId = null as any;

    this.adminService.addDriver(payload).subscribe({
      next: () => {
        this.toast.success('Driver added successfully');
        this.newDriver = { driverId: 0, name: '', licenseNumber: '', phone: '', officeId: 0, addressId: 0 };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add driver')
    });
  }

  editRow(driver: DriverDto): void {
    if (this.editingId === driver.driverId) return;
    this.editingId = driver.driverId;
    this.editDriver = { ...driver };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;
    this.adminService.updateDriver(this.editingId, this.editDriver).subscribe({
      next: () => {
        this.toast.success('Driver updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteDriver(id: number): void {
    if (!confirm('Delete this driver?')) return;
    this.adminService.deleteDriver(id).subscribe({
      next: () => {
        this.toast.success('Driver deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete')
    });
  }
}
