import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminAgenciesComponent } from './admin-agencies.component';

describe('AdminAgenciesComponent', () => {
  let component: AdminAgenciesComponent;
  let fixture: ComponentFixture<AdminAgenciesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminAgenciesComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getAgencies: () => of([]),
            addAgency: () => of({}),
            updateAgency: () => of({}),
            deleteAgency: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminAgenciesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
