import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { BookingDto, BookingResponse } from '../models/bus.model';

@Injectable({ providedIn: 'root' })
export class BookingService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  bookBus(dto: BookingDto): Observable<BookingResponse> {
    return this.http.post<BookingResponse>(`${this.apiUrl}/bus/addbusbooking`, dto);
  }

  getMyBookings(): Observable<BookingResponse[]> {
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/bus/bookings`);
  }

  cancelBooking(id: number): Observable<BookingResponse> {
    return this.http.put<BookingResponse>(`${this.apiUrl}/bus/bookings/${id}/cancel`, {});
  }

  getBookingsByStatus(status: string): Observable<BookingResponse[]> {
    const params = new HttpParams().set('status', status);
    return this.http.get<BookingResponse[]>(`${this.apiUrl}/bus/bookings/status`, { params });
  }
}
