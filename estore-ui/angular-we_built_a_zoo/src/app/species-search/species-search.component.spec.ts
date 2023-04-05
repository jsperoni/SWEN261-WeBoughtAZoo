import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpeciesSearchComponent } from './species-search.component';

describe('SpeciesSearchComponent', () => {
  let component: SpeciesSearchComponent;
  let fixture: ComponentFixture<SpeciesSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SpeciesSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpeciesSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
