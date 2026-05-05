import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgencyRequest, AgencyResponse } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-agencies',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-agencies.component.html',
  styleUrls: ['./admin-agencies.component.css']
})
export class AdminAgenciesComponent implements OnInit {
  agencies: AgencyResponse[] = [];
  loading = true;

  showAddForm = false;
  newAgency: AgencyRequest = { name: '', contactPersonName: '', email: '', phone: '' };

  editingId: number | null = null;
  editAgency: AgencyRequest = { name: '', contactPersonName: '', email: '', phone: '' };

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getAgencies().subscribe({
      next: (data) => { this.agencies = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load agencies'); this.loading = false; }
    });
  }

  addAgency(): void {
    if (!this.newAgency.name || !this.newAgency.contactPersonName || !this.newAgency.email) {
      this.toast.error('Please fill required fields');
      return;
    }
    this.adminService.addAgency(this.newAgency).subscribe({
      next: () => {
        this.toast.success('Agency added successfully');
        this.newAgency = { name: '', contactPersonName: '', email: '', phone: '' };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add agency')
    });
  }

  editRow(agency: AgencyResponse): void {
    if (this.editingId === agency.agencyId) return;
    this.editingId = agency.agencyId;
    this.editAgency = {
      name: agency.name,
      contactPersonName: agency.contactPersonName,
      email: agency.email,
      phone: agency.phone
    };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;
    this.adminService.updateAgency(this.editingId, this.editAgency).subscribe({
      next: () => {
        this.toast.success('Agency updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteAgency(id: number): void {
    if (!confirm('Delete this agency? This may fail if there are linked offices.')) return;
    this.adminService.deleteAgency(id).subscribe({
      next: () => {
        this.toast.success('Agency deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete (check dependencies)')
    });
  }
}
