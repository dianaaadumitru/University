import { TestBed } from '@angular/core/testing';

import { MovieappService } from './movieapp.service';

describe('MovieappService', () => {
  let service: MovieappService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovieappService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
