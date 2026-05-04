import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import {
  AgencyRequest, AgencyResponse,
  AgencyOfficeRequest, AgencyOfficeResponse,
  BusDto, DriverDto, RouteResponse, TripResponse
} from '../models/bus.model';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private apiUrl = `${environment.apiUrl}/admin`;

  constructor(private http: HttpClient) {}

  // ── Agencies ──
  getAgencies(): Observable<AgencyResponse[]> {
    return this.http.get<AgencyResponse[]>(`${this.apiUrl}/agencies`);
  }
  getAgencyById(id: number): Observable<AgencyResponse> {
    return this.http.get<AgencyResponse>(`${this.apiUrl}/agencies/${id}`);
  }
  addAgency(dto: AgencyRequest): Observable<AgencyResponse> {
    return this.http.post<AgencyResponse>(`${this.apiUrl}/agencies`, dto);
  }
  updateAgency(id: number, dto: AgencyRequest): Observable<AgencyResponse> {
    return this.http.put<AgencyResponse>(`${this.apiUrl}/agencies/${id}`, dto);
  }
  deleteAgency(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/agencies/${id}`, { responseType: 'text' });
  }

  // ── Agency Offices ──
  getOffices(): Observable<AgencyOfficeResponse[]> {
    return this.http.get<AgencyOfficeResponse[]>(`${this.apiUrl}/agency-offices`);
  }
  addOffice(dto: AgencyOfficeRequest): Observable<AgencyOfficeResponse> {
    return this.http.post<AgencyOfficeResponse>(`${this.apiUrl}/agency-offices`, dto);
  }
  updateOffice(id: number, dto: AgencyOfficeRequest): Observable<AgencyOfficeResponse> {
    return this.http.put<AgencyOfficeResponse>(`${this.apiUrl}/agency-offices/${id}`, dto);
  }
  deleteOffice(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/agency-offices/${id}`, { responseType: 'text' });
  }

  // ── Buses ──
  getBuses(): Observable<BusDto[]> {
    return this.http.get<BusDto[]>(`${this.apiUrl}/buses`);
  }
  addBus(dto: BusDto): Observable<BusDto> {
    return this.http.post<BusDto>(`${this.apiUrl}/buses`, dto);
  }
  updateBus(id: number, dto: BusDto): Observable<BusDto> {
    return this.http.put<BusDto>(`${this.apiUrl}/buses/${id}`, dto);
  }
  deleteBus(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/buses/${id}`, { responseType: 'text' });
  }

  // ── Drivers ──
  getDrivers(): Observable<DriverDto[]> {
    return this.http.get<DriverDto[]>(`${this.apiUrl}/drivers`);
  }
  addDriver(dto: DriverDto): Observable<DriverDto> {
    return this.http.post<DriverDto>(`${this.apiUrl}/drivers`, dto);
  }
  updateDriver(id: number, dto: DriverDto): Observable<DriverDto> {
    return this.http.put<DriverDto>(`${this.apiUrl}/drivers/${id}`, dto);
  }
  deleteDriver(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/drivers/${id}`, { responseType: 'text' });
  }

  // ── Routes ──
  getRoutes(): Observable<RouteResponse[]> {
    return this.http.get<RouteResponse[]>(`${this.apiUrl}/routes`);
  }
  addRoute(route: any): Observable<RouteResponse> {
    return this.http.post<RouteResponse>(`${this.apiUrl}/routes`, route);
  }
  updateRoute(id: number, route: any): Observable<RouteResponse> {
    return this.http.put<RouteResponse>(`${this.apiUrl}/routes/${id}`, route);
  }
  deleteRoute(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/routes/${id}`, { responseType: 'text' });
  }

  // ── Trips ──
  getTrips(): Observable<TripResponse[]> {
    return this.http.get<TripResponse[]>(`${this.apiUrl}/trips`);
  }
  addTrip(trip: any): Observable<TripResponse> {
    return this.http.post<TripResponse>(`${this.apiUrl}/trips`, trip);
  }
  updateTrip(id: number, trip: any): Observable<TripResponse> {
    return this.http.put<TripResponse>(`${this.apiUrl}/trips/${id}`, trip);
  }
  deleteTrip(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/trips/${id}`, { responseType: 'text' });
  }
}
