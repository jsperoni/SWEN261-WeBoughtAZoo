import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Species } from '../species';
import { SpeciesService } from '../species.service';

@Component({
  selector: 'app-species-search',
  templateUrl: './species-search.component.html',
  styleUrls: [ './species-search.component.css' ]
})
export class SpeciesSearchComponent implements OnInit {
  species$!: Observable<Species[]>;
  private searchTerms = new Subject<string>();

  constructor(private speciesService: SpeciesService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.species$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.speciesService.searchSpecies(term.toLowerCase())),
    );
  }
}