import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReviewResponse } from '../../models/bus.model';
import { ReviewService } from '../../services/review.service';
import { IconComponent } from '../../shared/icon.component';

@Component({
  selector: 'app-my-reviews',
  standalone: true,
  imports: [CommonModule, IconComponent],
  templateUrl: './my-reviews.component.html',
  styleUrls: ['./my-reviews.component.css']
})
export class MyReviewsComponent implements OnInit {
  reviews: ReviewResponse[] = [];
  loading = true;

  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.reviewService.getMyReviews().subscribe({
      next: (data) => { this.reviews = data; this.loading = false; },
      error: () => { this.loading = false; }
    });
  }
}
