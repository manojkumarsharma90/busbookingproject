import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  src = '';
  dest = '';
  date = '';
  minDate = new Date().toISOString().split('T')[0];

  constructor(private router: Router, private toast: ToastService) { }

  swapCities(): void {
    [this.src, this.dest] = [this.dest, this.src];
  }

  onSearch(): void {
    if (this.src && this.dest && this.date) {
      if (this.src.toLowerCase().trim() === this.dest.toLowerCase().trim()) {
        this.toast.error('Source and destination cannot be the same');
        return;
      }
      this.router.navigate(['/search'], {
        queryParams: { src: this.src, dest: this.dest, date: this.date }
      });
    }
  }
}
