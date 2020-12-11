import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { MusixmatchService } from './musixmatch.service';

describe('MusixmatchService', () => {
  let service: MusixmatchService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule]
    });
    service = TestBed.inject(MusixmatchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
