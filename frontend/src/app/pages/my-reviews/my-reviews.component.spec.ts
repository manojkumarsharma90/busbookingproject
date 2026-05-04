import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { ReviewService } from '../../services/review.service';
import { MyReviewsComponent } from './my-reviews.component';

describe('MyReviewsComponent', () => {
  let component: MyReviewsComponent;
  let fixture: ComponentFixture<MyReviewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyReviewsComponent],
      providers: [
        { provide: ReviewService, useValue: { getMyReviews: () => of([]) } }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MyReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
