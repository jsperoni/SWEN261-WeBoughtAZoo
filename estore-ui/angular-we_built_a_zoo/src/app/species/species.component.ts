import { Component, OnInit } from '@angular/core';

import { Species } from '../species';
//import { AnimalService } from '../animal.service'; will need a species service !!!
import { instance } from '../login/login.component';

@Component({
  selector: 'app-species',
  templateUrl: './species.component.html',
  styleUrls: ['./species.component.css']
})
export class SpeciesComponent implements OnInit {
  speciesList: Species[] = [];

  constructor(/*private speciesService: SpeciesService*/) { }

  ngOnInit(): void {
    this.getSpecies();
  }

  getSpecies(): void {
    /*this.speciesService.getSpecies()
    .subscribe(speciesList => this.speciesList = speciesList);*/
  }
  add(name: string, speciesInfo: string[]): void {
    name = name.trim();
    if (!name || !speciesInfo) { return; }
    /*this.speciesService.addSpecies({ name, speciesInfo} as Species)
      .subscribe(species => {
        this.speciesList.push(species);
      });*/
  }

  delete(species: Species): void {
    this.speciesList = this.speciesList.filter(h => h !== species);
    /*this.speciesService.deleteAnimal(species.name).subscribe();*/
  }
  
  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }
}
