import { TestBed } from '@angular/core/testing';
import { CollectorService } from './collector.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('CollectorService', () => {
  let service: CollectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(CollectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

});
