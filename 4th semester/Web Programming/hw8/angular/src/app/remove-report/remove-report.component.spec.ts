import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveReportComponent } from './remove-report.component';

describe('RemoveReportComponent', () => {
  let component: RemoveReportComponent;
  let fixture: ComponentFixture<RemoveReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RemoveReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
