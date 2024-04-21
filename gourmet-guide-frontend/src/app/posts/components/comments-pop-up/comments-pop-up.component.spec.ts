import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentsPopUpComponent } from './comments-pop-up.component';

describe('CommentsPopUpComponent', () => {
  let component: CommentsPopUpComponent;
  let fixture: ComponentFixture<CommentsPopUpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CommentsPopUpComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommentsPopUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
