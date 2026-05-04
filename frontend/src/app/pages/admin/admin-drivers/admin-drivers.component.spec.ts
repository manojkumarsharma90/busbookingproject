import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminDriversComponent } from './admin-drivers.component';

describe('AdminDriversComponent', () => {
  let component: AdminDriversComponent;
  let fixture: ComponentFixture<AdminDriversComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminDriversComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getOffices: () => of([]),
            getDrivers: () => of([]),
            addDriver: () => of({}),
            updateDriver: () => of({}),
            deleteDriver: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminDriversComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
