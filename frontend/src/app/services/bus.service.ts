import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TripResponse } from '../models/bus.model';
import { environment } from '../enviroment';

@Injectable({ providedIn: 'root' })
export class BusService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  searchTrips(src: string, dest: string, date: string): Observable<TripResponse[]> {
    const params = new HttpParams()
      .set('src', src)
      .set('dest', dest)
      .set('date', date);
    return this.http.get<TripResponse[]>(`${this.apiUrl}/bus/schedules`, { params });
  }

  getTripById(id: number): Observable<TripResponse> {
    return this.http.get<TripResponse>(`${this.apiUrl}/bus/schedules/${id}`);
  }

  getBookedSeats(tripId: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/bus/schedules/${tripId}/seats`);
  }

  searchWithFilter(src: string, dest: string, date: string, min: number, max: number): Observable<TripResponse[]> {
    const params = new HttpParams()
      .set('src', src)
      .set('dest', dest)
      .set('date', date)
      .set('min', min.toString())
      .set('max', max.toString());
    return this.http.get<TripResponse[]>(`${this.apiUrl}/bus/schedules/filter`, { params });
  }
}
