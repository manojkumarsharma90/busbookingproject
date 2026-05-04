export interface TripResponse {
  tripId: number;
  routeId: number;
  source: string;
  destination: string;
  busId: number;
  busNumber: string;
  busType: string;
  departureTime: string;
  tripDate: string;
  availableSeats: number;
  fare: number;
}

export interface PassengerDto {
  name: string;
  age: number;
  seatNo: string;
  gender: string;
}

export interface BookingDto {
  tripId: number;
  passenger: PassengerDto[];
}

export interface BookingResponse {
  bookingId: number;
  status: string;
  bookingDate: string;
  customerName: string;
  totalSeats: number;
  trip: TripResponse;
  passengers: PassengerDto[];
}

export interface ReviewRequest {
  customerId: number;
  tripId: number;
  rating: number;
  comment: string;
}

export interface ReviewResponse {
  tripId: number;
  rating: number;
  comment: string;
}

export interface RouteResponse {
  routeId: number;
  fromCity: string;
  toCity: string;
  breakPoints: number;
  duration: number;
}

export interface BusDto {
  busId: number;
  registrationNumber: string;
  type: string;
  capacity: number;
  officeId: number;
}

export interface DriverDto {
  driverId: number;
  licenseNumber: string;
  name: string;
  phone: string;
  officeId: number;
  addressId: number;
}

export interface AgencyRequest {
  name: string;
  contactPersonName: string;
  email: string;
  phone: string;
}

export interface AgencyResponse {
  agencyId: number;
  name: string;
  contactPersonName: string;
  email: string;
  phone: string;
}

export interface AgencyOfficeRequest {
  officeMail: string;
  officeContactPersonName: string;
  officeContactNumber: string;
  agencyId: number;
  officeAddressId?: number;
}

export interface AgencyOfficeResponse {
  officeId: number;
  officeMail: string;
  officeContactPersonName: string;
  officeContactNumber: string;
  agencyId: number;
  agencyName: string;
  officeAddressId?: number;
}
