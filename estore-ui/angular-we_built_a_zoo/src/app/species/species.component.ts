import { Component, OnInit } from '@angular/core';

import { Species } from '../species';
import { SpeciesService } from '../species.service';
import { instance } from '../login/login.component';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-species',
  templateUrl: './species.component.html',
  styleUrls: ['./species.component.css']
})
export class SpeciesComponent implements OnInit {
  speciesList: Species[] = [];
  speciesBought: string[] = [];

  constructor(private speciesService: SpeciesService, 
              private customerService: CustomerService) { }

  ngOnInit(): void {
    this.getTargets();
    this.getSpeciess();
  }

  getTargets(): void{
    if(instance.customer?.id){
      this.customerService.getCustomerSpeciesHistory(instance.customer?.id)
        .subscribe(speciesBought => this.speciesBought = speciesBought);
    }
  }

  getSpeciess(): void {
    this.speciesService.getSpeciess()
    .subscribe(speciesList => this.speciesList = speciesList);
  }
  add(name: string, speciesInfo: string): void {
    console.log("Tried to add a species");
    name = name.trim();
    let info = [speciesInfo];
    if (!name || !info) { return; }
    this.speciesService.addSpecies({ name, info } as Species)
      .subscribe(species => {
        this.speciesList.push(species);
      });
  }

  delete(species: Species): void {
    this.speciesList = this.speciesList.filter(h => h !== species);
    this.speciesService.deleteSpecies(species.name).subscribe();
  }
  
  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }

  isUser(): boolean {
    if(instance.customer?.username){
      return true;
    } else{
      return false;
    }
  }
}
