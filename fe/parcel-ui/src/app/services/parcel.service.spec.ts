import { TestBed } from '@angular/core/testing';

import { ParcelRestService } from './parcel-rest.service';

describe('ParcelService', () => {
  let service: ParcelRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParcelRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
