# 🚌 Bus Ticket Booking System — REST API

A production-ready Spring Boot backend for a Bus Ticket Booking System with JWT authentication, role-based access control (Admin & User), and complete CRUD operations.

---

## 📋 Table of Contents

- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Default Credentials](#-default-credentials)
- [Package Structure](#-package-structure)
- [API Endpoints Overview](#-api-endpoints-overview)
- [Postman Testing Guide](#-postman-testing-guide)
  - [1. Authentication APIs](#1-authentication-apis)
  - [2. Admin — Agency APIs](#2-admin--agency-apis)
  - [3. Admin — Agency Office APIs](#3-admin--agency-office-apis)
  - [4. Admin — Bus APIs](#4-admin--bus-apis)
  - [5. Admin — Route APIs](#5-admin--route-apis)
  - [6. Admin — Driver APIs](#6-admin--driver-apis)
  - [7. Admin — Trip APIs](#7-admin--trip-apis)
  - [8. User — Trip Search APIs](#8-user--trip-search-apis)
  - [9. User — Booking APIs](#9-user--booking-apis)
  - [10. Review APIs](#10-review-apis)

---

## 🛠 Tech Stack

| Technology        | Version  |
|-------------------|----------|
| Java              | 17       |
| Spring Boot       | 3.4.5    |
| Spring Security   | 6.x      |
| Spring Data JPA   | 3.x      |
| PostgreSQL        | 15+      |
| JWT (jjwt)        | 0.12.6   |
| Maven             | 3.9+     |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- PostgreSQL running on `localhost:5432`
- A database named `busticketbooking`

### Setup

1. **Create the database and seed data:**
   ```sql
   psql -U postgres -f BusTicketBooking.sql
   ```

2. **Configure database credentials** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/busticketbooking
   spring.datasource.username=postgres
   spring.datasource.password=12345678
   ```

3. **Build and Run:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The application starts on **http://localhost:8080**

---

## 🔑 Default Credentials

| Role  | Username | Password   |
|-------|----------|------------|
| ADMIN | `admin`  | `admin123` |

> The admin user is auto-created on first startup by the `DataSeeder` component.

---

## 📦 Package Structure

```
com.busbooking/
├── config/           → SecurityConfig, DataSeeder
├── controller/       → AdminController, BusBookingController, LoginController, SignUpController
├── dto/              → BookingDto, PassengerDto, SignUpRequestDto, LoginResponseDto, ErrorMessageDto, ReviewDto
├── entity/           → JPA Entities
├── enums/            → Role enum (ADMIN, USER)
├── exception/        → Custom exceptions + GlobalExceptionHandler
├── repo/             → JPA Repositories (XxxRepo)
├── security/         → AuthRequest, JwtService, JwtAuthFilter
└── service/          → BusBookingService + BusBookingServiceImpl
```

---

## 📡 API Endpoints Overview

| Category      | Method | Endpoint                              | Auth Required | Role   |
|---------------|--------|---------------------------------------|---------------|--------|
| **Auth**      | POST   | `/auth/signup`                        | ❌            | Public |
|               | POST   | `/login`                              | ❌            | Public |
| **Agencies**  | GET    | `/admin/agencies`                     | ✅            | ADMIN  |
|               | GET    | `/admin/agencies/{id}`                | ✅            | ADMIN  |
|               | POST   | `/admin/agencies`                     | ✅            | ADMIN  |
|               | PUT    | `/admin/agencies/{id}`                | ✅            | ADMIN  |
|               | DELETE | `/admin/agencies/{id}`                | ✅            | ADMIN  |
| **Offices**   | GET    | `/admin/agency-offices`               | ✅            | ADMIN  |
|               | GET    | `/admin/agency-offices/{id}`          | ✅            | ADMIN  |
|               | GET    | `/admin/agency-offices/agency/{agencyId}` | ✅        | ADMIN  |
|               | POST   | `/admin/agency-offices`               | ✅            | ADMIN  |
|               | PUT    | `/admin/agency-offices/{id}`          | ✅            | ADMIN  |
|               | DELETE | `/admin/agency-offices/{id}`          | ✅            | ADMIN  |
| **Buses**     | GET    | `/admin/buses`                        | ✅            | ADMIN  |
|               | GET    | `/admin/buses/{id}`                   | ✅            | ADMIN  |
|               | GET    | `/admin/buses/office/{officeId}`      | ✅            | ADMIN  |
|               | POST   | `/admin/buses`                        | ✅            | ADMIN  |
|               | PUT    | `/admin/buses/{id}`                   | ✅            | ADMIN  |
|               | DELETE | `/admin/buses/{id}`                   | ✅            | ADMIN  |
| **Routes**    | GET    | `/admin/routes`                       | ✅            | ADMIN  |
|               | GET    | `/admin/routes/{id}`                  | ✅            | ADMIN  |
|               | POST   | `/admin/routes`                       | ✅            | ADMIN  |
|               | PUT    | `/admin/routes/{id}`                  | ✅            | ADMIN  |
|               | DELETE | `/admin/routes/{id}`                  | ✅            | ADMIN  |
| **Drivers**   | GET    | `/admin/drivers`                      | ✅            | ADMIN  |
|               | GET    | `/admin/drivers/{id}`                 | ✅            | ADMIN  |
|               | GET    | `/admin/drivers/office/{officeId}`    | ✅            | ADMIN  |
|               | POST   | `/admin/drivers`                      | ✅            | ADMIN  |
|               | PUT    | `/admin/drivers/{id}`                 | ✅            | ADMIN  |
|               | DELETE | `/admin/drivers/{id}`                 | ✅            | ADMIN  |
| **Trips**     | GET    | `/admin/trips`                        | ✅            | ADMIN  |
|               | GET    | `/admin/trips/{id}`                   | ✅            | ADMIN  |
|               | POST   | `/admin/trips`                        | ✅            | ADMIN  |
|               | PUT    | `/admin/trips/{id}`                   | ✅            | ADMIN  |
|               | DELETE | `/admin/trips/{id}`                   | ✅            | ADMIN  |
| **Search**    | GET    | `/bus/schedules?src=X&dest=Y&date=Z`  | ❌            | Public |
|               | GET    | `/bus/schedules/{id}`                 | ✅            | Any    |
|               | GET    | `/bus/schedules/{id}/seats`           | ✅            | Any    |
| **Bookings**  | POST   | `/bus/addbusbooking`                  | ✅            | USER   |
|               | GET    | `/bus/bookings`                       | ✅            | USER   |
|               | PUT    | `/bus/bookings/{id}/cancel`           | ✅            | USER   |
| **Reviews**   | POST   | `/bus/reviews`                        | ✅            | USER   |
|               | GET    | `/bus/reviews/trip/{tripId}`          | ✅            | Any    |
|               | GET    | `/bus/reviews/my`                     | ✅            | USER   |

---

## 🧪 Postman Testing Guide

### ⚙️ Postman Setup

1. **Create a Postman Environment** with variable:
   - `baseUrl` = `http://localhost:8080`
   - `token` = *(will be set after login)*

2. **For authenticated requests**, add this header:
   ```
   Authorization: Bearer {{token}}
   ```

3. **All request bodies** use `Content-Type: application/json`

---

### 1. Authentication APIs

#### 1.1 Register a New User

```
POST {{baseUrl}}/auth/signup
```

**Request Body:**
```json
{
    "name": "Rahul Sharma",
    "userName": "rahul_sharma",
    "email": "rahul.sharma@gmail.com",
    "phoneNo": "9876543210",
    "password": "password123",
    "address": "Sapphire Towers, Malleswaram",
    "city": "Bangalore",
    "state": "Karnataka",
    "zipCode": "560003"
}
```

**Expected Response (201 Created):**
```json
{
    "message": "User registered successfully"
}
```

---

#### 1.2 Login as User

```
POST {{baseUrl}}/login
```

**Request Body:**
```json
{
    "username": "rahul_sharma",
    "password": "password123"
}
```

**Expected Response (200 OK):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "msg": "user authenticated",
    "timestamp": "2026-04-27T15:20:00",
    "userName": "rahul_sharma",
    "role": "USER"
}
```

> 📌 **Copy the `token` value** and save it in your Postman environment variable `token`.

---

#### 1.3 Login as Admin

```
POST {{baseUrl}}/login
```

**Request Body:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```

**Expected Response (200 OK):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "msg": "user authenticated",
    "timestamp": "2026-04-27T15:20:00",
    "userName": "admin",
    "role": "ADMIN"
}
```

> 📌 **Use the admin token** for all `/admin/**` endpoints below.

---

#### 1.4 Register with Duplicate Username

```
POST {{baseUrl}}/auth/signup
```

**Request Body:**
```json
{
    "name": "Another Rahul",
    "userName": "rahul_sharma",
    "email": "different@gmail.com",
    "phoneNo": "9876543211",
    "password": "password123"
}
```

**Expected Response (409 Conflict):**
```json
{
    "errormsg": "Username already exists",
    "timeStamp": "2026-04-27T15:22:00",
    "status": "409 CONFLICT"
}
```

---

### 2. Admin — Agency APIs

> ⚠️ **All admin endpoints require the Admin JWT token in the Authorization header.**

#### 2.1 Get All Agencies

```
GET {{baseUrl}}/admin/agencies
```

**Headers:**
```
Authorization: Bearer {{admin_token}}
```

**Expected Response (200 OK):**
```json
[
    {
        "agencyId": 1,
        "name": "Indian Travels",
        "contactPersonName": "Rahul Sharma",
        "email": "rahul@indiantravels.com",
        "phone": "9876543210"
    },
    {
        "agencyId": 2,
        "name": "Royal Tours",
        "contactPersonName": "Priya Singh",
        "email": "priya@royaltours.in",
        "phone": "8765432109"
    }
]
```

---

#### 2.2 Get Agency by ID

```
GET {{baseUrl}}/admin/agencies/1
```

---

#### 2.3 Create New Agency

```
POST {{baseUrl}}/admin/agencies
```

**Request Body:**
```json
{
    "name": "Metro Express",
    "contactPersonName": "Amit Verma",
    "email": "amit@metroexpress.com",
    "phone": "9988776655"
}
```

**Expected Response (200 OK):** — returns the saved agency entity directly

---

#### 2.4 Update Agency

```
PUT {{baseUrl}}/admin/agencies/11
```

**Request Body:**
```json
{
    "name": "Metro Express Travels",
    "contactPersonName": "Amit Kumar Verma",
    "email": "amit.updated@metroexpress.com",
    "phone": "9988776600"
}
```

---

#### 2.5 Delete Agency

```
DELETE {{baseUrl}}/admin/agencies/11
```

**Expected Response (200 OK):**
```
Agency deleted successfully
```

---

### 3. Admin — Agency Office APIs

#### 3.1 Get All Agency Offices

```
GET {{baseUrl}}/admin/agency-offices
```

---

#### 3.2 Get Offices by Agency ID

```
GET {{baseUrl}}/admin/agency-offices/agency/1
```

---

#### 3.3 Create Agency Office

```
POST {{baseUrl}}/admin/agency-offices
```

**Request Body (provide agency as nested object):**
```json
{
    "agency": { "agencyId": 1 },
    "officeMail": "info-jaipur@indiantravels.com",
    "officeContactPersonName": "Suresh Kumar",
    "officeContactNumber": "9876543299"
}
```

---

#### 3.4 Update Agency Office

```
PUT {{baseUrl}}/admin/agency-offices/1
```

**Request Body:**
```json
{
    "agency": { "agencyId": 1 },
    "officeMail": "mumbai-updated@indiantravels.com",
    "officeContactPersonName": "Rakesh Verma",
    "officeContactNumber": "9876543999"
}
```

---

#### 3.5 Delete Agency Office

```
DELETE {{baseUrl}}/admin/agency-offices/16
```

---

### 4. Admin — Bus APIs

#### 4.1 Get All Buses

```
GET {{baseUrl}}/admin/buses
```

---

#### 4.2 Get Buses by Office

```
GET {{baseUrl}}/admin/buses/office/1
```

---

#### 4.3 Create New Bus

```
POST {{baseUrl}}/admin/buses
```

**Request Body:**
```json
{
    "office": { "officeId": 1 },
    "registrationNumber": "MH01ZZ9999",
    "capacity": 45,
    "type": "AC Sleeper"
}
```

---

#### 4.4 Update Bus

```
PUT {{baseUrl}}/admin/buses/1
```

**Request Body:**
```json
{
    "office": { "officeId": 1 },
    "registrationNumber": "MH01AB1234",
    "capacity": 52,
    "type": "Seater"
}
```

---

#### 4.5 Delete Bus

```
DELETE {{baseUrl}}/admin/buses/76
```

---

### 5. Admin — Route APIs

#### 5.1 Get All Routes

```
GET {{baseUrl}}/admin/routes
```

---

#### 5.2 Create New Route

```
POST {{baseUrl}}/admin/routes
```

**Request Body:**
```json
{
    "fromCity": "Bangalore",
    "toCity": "Goa",
    "breakPoints": 2,
    "duration": 10
}
```

---

#### 5.3 Update Route

```
PUT {{baseUrl}}/admin/routes/1
```

**Request Body:**
```json
{
    "fromCity": "Mumbai",
    "toCity": "Pune",
    "breakPoints": 2,
    "duration": 4
}
```

---

#### 5.4 Delete Route

```
DELETE {{baseUrl}}/admin/routes/50
```

---

### 6. Admin — Driver APIs

#### 6.1 Get All Drivers

```
GET {{baseUrl}}/admin/drivers
```

---

#### 6.2 Get Drivers by Office

```
GET {{baseUrl}}/admin/drivers/office/1
```

---

#### 6.3 Create New Driver

```
POST {{baseUrl}}/admin/drivers
```

**Request Body:**
```json
{
    "office": { "officeId": 1 },
    "licenseNumber": "DL999999",
    "name": "Vikash Yadav",
    "phone": "9876500001"
}
```

---

#### 6.4 Update Driver

```
PUT {{baseUrl}}/admin/drivers/1
```

**Request Body:**
```json
{
    "office": { "officeId": 1 },
    "licenseNumber": "DL123456",
    "name": "Raj Kumar Singh",
    "phone": "9876543211"
}
```

---

#### 6.5 Delete Driver

```
DELETE {{baseUrl}}/admin/drivers/100
```

---

### 7. Admin — Trip APIs

#### 7.1 Get All Trips

```
GET {{baseUrl}}/admin/trips
```

---

#### 7.2 Create New Trip

```
POST {{baseUrl}}/admin/trips
```

**Request Body:**
```json
{
    "route": { "routeId": 1 },
    "bus": { "busId": 6 },
    "boardingAddress": { "addressId": 7 },
    "droppingAddress": { "addressId": 2 },
    "driver1": { "driverId": 10 },
    "driver2": { "driverId": 11 },
    "departureTime": "2025-06-15T06:00:00",
    "arrivalTime": "2025-06-15T09:30:00",
    "tripDate": "2025-06-15T00:00:00",
    "availableSeats": 50,
    "fare": 650.00
}
```

---

#### 7.3 Update Trip

```
PUT {{baseUrl}}/admin/trips/1
```

**Request Body:**
```json
{
    "route": { "routeId": 1 },
    "bus": { "busId": 1 },
    "departureTime": "2024-01-15T08:00:00",
    "arrivalTime": "2024-01-15T11:00:00",
    "tripDate": "2024-01-15T00:00:00",
    "availableSeats": 5,
    "fare": 550.00
}
```

---

#### 7.4 Delete Trip

```
DELETE {{baseUrl}}/admin/trips/7
```

---

### 8. User — Trip Search APIs

#### 8.1 Search Trips (Public — No Auth Required)

```
GET {{baseUrl}}/bus/schedules?src=Mumbai&dest=Pune&date=2024-01-15
```

**No headers required.**

**Expected Response (200 OK):** — returns list of Trip entities directly
```json
[
    {
        "tripId": 1,
        "route": { "routeId": 1, "fromCity": "Mumbai", "toCity": "Pune", ... },
        "bus": { "busId": 1, "registrationNumber": "MH01AB1234", "type": "Seater", ... },
        "availableSeats": 0,
        "fare": 500.00,
        "departureTime": "2024-01-15T08:00:00",
        "arrivalTime": "2024-01-15T08:30:00",
        ...
    }
]
```

---

#### 8.2 Get Trip by ID

```
GET {{baseUrl}}/bus/schedules/4
```

**Headers:**
```
Authorization: Bearer {{token}}
```

---

#### 8.3 Get Booked Seats for a Trip

```
GET {{baseUrl}}/bus/schedules/4/seats
```

**Headers:**
```
Authorization: Bearer {{token}}
```

**Expected Response (200 OK):** — returns list of booked seat numbers
```json
["1", "5", "12"]
```

> Seats NOT in this list are available for booking.

---

### 9. User — Booking APIs

> 🔒 **All booking endpoints require the User JWT token.**

#### 9.1 Book Seats with Passenger Details

```
POST {{baseUrl}}/bus/addbusbooking
```

**Headers:**
```
Authorization: Bearer {{user_token}}
Content-Type: application/json
```

**Request Body:**
```json
{
    "tripId": 4,
    "passenger": [
        {
            "seatNo": "1",
            "name": "Rahul Sharma",
            "age": 28,
            "gender": "Male"
        },
        {
            "seatNo": "2",
            "name": "Priya Sharma",
            "age": 25,
            "gender": "Female"
        },
        {
            "seatNo": "3",
            "name": "Aryan Sharma",
            "age": 5,
            "gender": "Male"
        }
    ]
}
```

**Expected Response (201 Created):** — returns Booking entity directly

---

#### 9.2 Book Already Taken Seat (Error Test)

```
POST {{baseUrl}}/bus/addbusbooking
```

**Request Body:**
```json
{
    "tripId": 4,
    "passenger": [
        {
            "seatNo": "1",
            "name": "Someone Else",
            "age": 30,
            "gender": "Male"
        }
    ]
}
```

**Expected Response (400 Bad Request):**
```json
{
    "errormsg": "Seat 1 is already booked",
    "timeStamp": "2026-04-27T15:22:00",
    "status": "400 BAD_REQUEST"
}
```

---

#### 9.3 View My Bookings

```
GET {{baseUrl}}/bus/bookings
```

**Headers:**
```
Authorization: Bearer {{user_token}}
```

---

#### 9.4 Cancel a Booking

```
PUT {{baseUrl}}/bus/bookings/141/cancel
```

**Headers:**
```
Authorization: Bearer {{user_token}}
```

**Expected Response (200 OK):** — returns updated Booking entity

---

### 10. Review APIs

#### 10.1 Add a Review

```
POST {{baseUrl}}/bus/reviews
```

**Headers:**
```
Authorization: Bearer {{user_token}}
Content-Type: application/json
```

**Request Body:**
```json
{
    "tripId": 4,
    "rating": 5,
    "comment": "Excellent journey! Very comfortable AC seater bus."
}
```

**Expected Response (201 Created):** — returns Review entity

---

#### 10.2 Get Reviews for a Trip

```
GET {{baseUrl}}/bus/reviews/trip/4
```

---

#### 10.3 Get My Reviews

```
GET {{baseUrl}}/bus/reviews/my
```

---

## 🔐 Authorization Error Responses

All errors use the `ErrorMessageDto` format:

```json
{
    "errormsg": "error description",
    "timeStamp": "2026-04-27T15:22:00",
    "status": "404 NOT_FOUND"
}
```

---

## 📊 Complete User Journey — Step-by-Step Testing

| Step | Action | Endpoint | Method |
|------|--------|----------|--------|
| 1 | Register a new user | `/auth/signup` | POST |
| 2 | Login as user (save token) | `/login` | POST |
| 3 | Search for a trip | `/bus/schedules?src=Mumbai&dest=Pune&date=2024-01-15` | GET |
| 4 | View booked seats | `/bus/schedules/4/seats` | GET |
| 5 | Book seats with passengers | `/bus/addbusbooking` | POST |
| 6 | View my bookings | `/bus/bookings` | GET |
| 7 | Cancel a booking | `/bus/bookings/{id}/cancel` | PUT |
| 8 | Add a review | `/bus/reviews` | POST |
| 9 | View trip reviews | `/bus/reviews/trip/4` | GET |

---

## 📊 Admin Operations — Step-by-Step Testing

| Step | Action | Endpoint | Method |
|------|--------|----------|--------|
| 1 | Login as admin (save token) | `/login` | POST |
| 2 | View all agencies | `/admin/agencies` | GET |
| 3 | Create a new agency | `/admin/agencies` | POST |
| 4 | Edit the agency | `/admin/agencies/{id}` | PUT |
| 5 | Add an office to agency | `/admin/agency-offices` | POST |
| 6 | Add a bus to office | `/admin/buses` | POST |
| 7 | Add a route | `/admin/routes` | POST |
| 8 | Add a driver | `/admin/drivers` | POST |
| 9 | Create a trip | `/admin/trips` | POST |
| 10 | View all trips | `/admin/trips` | GET |
| 11 | Delete a trip | `/admin/trips/{id}` | DELETE |

---

## 👤 Author

Bus Ticket Booking System — Spring Boot REST API

---
