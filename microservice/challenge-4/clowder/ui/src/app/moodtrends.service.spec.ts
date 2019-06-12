import { TestBed } from '@angular/core/testing';

import { MoodtrendsService } from './moodtrends.service';

describe('MoodtrendsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MoodtrendsService = TestBed.get(MoodtrendsService);
    expect(service).toBeTruthy();
  });
});
