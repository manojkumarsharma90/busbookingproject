import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminOfficesComponent } from './admin-offices.component';

describe('AdminOfficesComponent', () => {
  let component: AdminOfficesComponent;
  let fixture: ComponentFixture<AdminOfficesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminOfficesComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getAgencies: () => of([]),
            getOffices: () => of([]),
            addOffice: () => of({}),
            updateOffice: () => of({}),
            deleteOffice: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminOfficesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
