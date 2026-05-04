import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { ReviewRequest, ReviewResponse } from '../models/bus.model';

@Injectable({ providedIn: 'root' })
export class ReviewService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  addReview(dto: ReviewRequest): Observable<ReviewResponse> {
    return this.http.post<ReviewResponse>(`${this.apiUrl}/bus/reviews`, dto);
  }

  getReviewsByTrip(tripId: number): Observable<ReviewResponse[]> {
    return this.http.get<ReviewResponse[]>(`${this.apiUrl}/bus/reviews/trip/${tripId}`);
  }

  getMyReviews(): Observable<ReviewResponse[]> {
    return this.http.get<ReviewResponse[]>(`${this.apiUrl}/bus/reviews/my`);
  }
}
