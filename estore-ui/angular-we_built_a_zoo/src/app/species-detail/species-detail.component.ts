import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Species } from '../species';
//import { SpeciesService } from '../species.service';

import { instance } from '../login/login.component';

let components: SpeciesDetailComponent[] = [];

@Component({
  selector: 'app-species-detail',
  templateUrl: './species-detail.component.html',
  styleUrls: [ './species-detail.component.css' ]
})
export class SpeciesDetailComponent implements OnInit {
  species: Species | undefined;
  username?: string = instance.customer?.username;

  constructor(
    private route: ActivatedRoute,
    /*private speciesService: SpeciesService,*/
    private location: Location
  ) {

  }

  ngOnInit(): void {
    this.getSpecies();
  }

  getSpecies(): void {
    const name = this.route.snapshot.paramMap.get('name');
    /*this.speciesService.getSpecies(name)
      .subscribe(species => this.species = species);*/
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.species) {
      /*this.speciesService.updateSpecies(this.species)
        .subscribe(() => this.goBack());*/
    }
  }

  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }
}

