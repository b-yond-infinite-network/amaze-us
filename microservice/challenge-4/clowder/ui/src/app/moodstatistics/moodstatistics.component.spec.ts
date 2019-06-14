import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MoodstatisticsComponent } from './moodstatistics.component';

describe('MoodstatisticsComponent', () => {
  let component: MoodstatisticsComponent;
  let fixture: ComponentFixture<MoodstatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MoodstatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MoodstatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
