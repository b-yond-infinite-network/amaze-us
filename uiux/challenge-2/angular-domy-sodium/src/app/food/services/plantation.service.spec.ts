import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { PlantationService } from './plantation.service';

describe('AuthenticationService', () => {
  let service: PlantationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(PlantationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

});
