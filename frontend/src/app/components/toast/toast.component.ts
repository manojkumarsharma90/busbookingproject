import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Toast, ToastService } from '../../services/toast.service';
import { IconComponent } from '../../shared/icon.component';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule, IconComponent],
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css']
})
export class ToastComponent implements OnInit, OnDestroy {
  toasts: Toast[] = [];
  private sub!: Subscription;

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.sub = this.toastService.toast$.subscribe(toast => {
      this.toasts.push(toast);
      setTimeout(() => this.toasts.shift(), 4000);
    });
  }

  remove(index: number): void {
    this.toasts.splice(index, 1);
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }
}
