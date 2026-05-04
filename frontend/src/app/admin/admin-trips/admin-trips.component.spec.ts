import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminTripsComponent } from './admin-trips.component';

describe('AdminTripsComponent', () => {
  let component: AdminTripsComponent;
  let fixture: ComponentFixture<AdminTripsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminTripsComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getRoutes: () => of([]),
            getBuses: () => of([]),
            getTrips: () => of([]),
            addTrip: () => of({}),
            updateTrip: () => of({}),
            deleteTrip: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminTripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
