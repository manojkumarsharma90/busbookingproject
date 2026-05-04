import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { BookingService } from '../../services/booking.service';
import { BusService } from '../../services/bus.service';
import { ToastService } from '../../services/toast.service';
import { BookComponent } from './book.component';

describe('BookComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;

  beforeEach(async () => {
    const tripStub = {
      availableSeats: 10,
      fare: 100,
      source: 'A',
      destination: 'B',
      busNumber: 'BUS-1',
      busType: 'AC',
      tripDate: '2026-01-01',
      departureTime: '2026-01-01T10:00:00'
    };

    await TestBed.configureTestingModule({
      imports: [BookComponent],
      providers: [
        { provide: ActivatedRoute, useValue: { snapshot: { paramMap: { get: () => '1' } } } },
        { provide: Router, useValue: jasmine.createSpyObj('Router', ['navigate']) },
        { provide: BusService, useValue: { getTripById: () => of(tripStub), getBookedSeats: () => of([]) } },
        { provide: BookingService, useValue: { bookBus: () => of({}) } },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } },
        { provide: AuthService, useValue: { isAdmin: () => false, isLoggedIn: () => true } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
