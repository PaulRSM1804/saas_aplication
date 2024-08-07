import { TestBed } from '@angular/core/testing';

import { RdfServiceService } from './rdf-service.service';

describe('RdfServiceService', () => {
  let service: RdfServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RdfServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
