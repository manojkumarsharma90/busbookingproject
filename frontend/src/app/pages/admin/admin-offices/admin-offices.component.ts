import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AgencyOfficeRequest, AgencyOfficeResponse, AgencyResponse } from '../../../models/bus.model';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';

@Component({
  selector: 'app-admin-offices',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-offices.component.html',
  styleUrls: ['./admin-offices.component.css']
})
export class AdminOfficesComponent implements OnInit {
  offices: AgencyOfficeResponse[] = [];
  agencies: AgencyResponse[] = [];
  loading = true;

  showAddForm = false;
  newOffice: AgencyOfficeRequest = { officeMail: '', officeContactPersonName: '', officeContactNumber: '', agencyId: 0 };

  editingId: number | null = null;
  editOffice: AgencyOfficeRequest = { officeMail: '', officeContactPersonName: '', officeContactNumber: '', agencyId: 0 };

  constructor(private adminService: AdminService, private toast: ToastService) {}

  ngOnInit(): void {
    // Load agencies first for the dropdown
    this.adminService.getAgencies().subscribe(data => {
      this.agencies = data;
      this.loadData();
    });
  }

  loadData(): void {
    this.loading = true;
    this.adminService.getOffices().subscribe({
      next: (data) => { this.offices = data; this.loading = false; },
      error: () => { this.toast.error('Failed to load offices'); this.loading = false; }
    });
  }

  getAgencyName(agencyId: number): string {
    const agency = this.agencies.find(a => a.agencyId === agencyId);
    return agency ? agency.name : 'Unknown';
  }

  addOffice(): void {
    if (!this.newOffice.agencyId || !this.newOffice.officeMail) {
      this.toast.error('Please fill required fields');
      return;
    }
    this.adminService.addOffice(this.newOffice).subscribe({
      next: () => {
        this.toast.success('Office added successfully');
        this.newOffice = { officeMail: '', officeContactPersonName: '', officeContactNumber: '', agencyId: 0 };
        this.showAddForm = false;
        this.loadData();
      },
      error: () => this.toast.error('Failed to add office')
    });
  }

  editRow(office: AgencyOfficeResponse): void {
    if (this.editingId === office.officeId) return;
    this.editingId = office.officeId;
    this.editOffice = {
      officeMail: office.officeMail,
      officeContactPersonName: office.officeContactPersonName,
      officeContactNumber: office.officeContactNumber,
      agencyId: office.agencyId
    };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(): void {
    if (!this.editingId) return;
    this.adminService.updateOffice(this.editingId, this.editOffice).subscribe({
      next: () => {
        this.toast.success('Office updated');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Update failed')
    });
  }

  deleteOffice(id: number): void {
    if (!confirm('Delete this office?')) return;
    this.adminService.deleteOffice(id).subscribe({
      next: () => {
        this.toast.success('Office deleted');
        this.editingId = null;
        this.loadData();
      },
      error: () => this.toast.error('Failed to delete')
    });
  }
}
