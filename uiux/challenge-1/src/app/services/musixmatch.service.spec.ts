import { TestBed } from '@angular/core/testing';

import { MusixmatchService } from './musixmatch.service';

describe('MusixmatchService', () => {
  let service: MusixmatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MusixmatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
