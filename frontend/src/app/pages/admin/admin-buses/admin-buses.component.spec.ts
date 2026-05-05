import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminBusesComponent } from './admin-buses.component';

describe('AdminBusesComponent', () => {
  let component: AdminBusesComponent;
  let fixture: ComponentFixture<AdminBusesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminBusesComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getOffices: () => of([]),
            getBuses: () => of([]),
            addBus: () => of({}),
            updateBus: () => of({}),
            deleteBus: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminBusesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
