import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { AdminRoutesComponent } from './admin-routes.component';

describe('AdminRoutesComponent', () => {
  let component: AdminRoutesComponent;
  let fixture: ComponentFixture<AdminRoutesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRoutesComponent],
      providers: [
        {
          provide: AdminService,
          useValue: {
            getRoutes: () => of([]),
            addRoute: () => of({}),
            updateRoute: () => of({}),
            deleteRoute: () => of({})
          }
        },
        { provide: ToastService, useValue: { success: () => {}, error: () => {} } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminRoutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
