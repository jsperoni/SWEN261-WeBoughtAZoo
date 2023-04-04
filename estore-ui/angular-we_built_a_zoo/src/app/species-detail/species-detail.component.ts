import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Species } from '../species';
import { SpeciesService } from '../species.service';

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
  container = document.getElementById('input-cont');
  inps: number = 0;

  constructor(
    private route: ActivatedRoute,
    private speciesService: SpeciesService,
    private location: Location
  ) {

  }

  ngOnInit(): void {
    this.getSpecies();
  }

  getSpecies(): void {
    const name = this.route.snapshot.paramMap.get('name');
    this.speciesService.getSpecies(name == null ? "" : name as string)
      .subscribe(species => this.species = species);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.species) {
      this.nameInputs();
      let info: string[] = [];
      for(let i = 1; i < this.inps; i++){
        let inp = document.getElementsByTagName("input").item(i)?.value;
        if(inp){
          info.push(inp);
        }
      }
      this.species.info = info;
      this.speciesService.updateSpecies(this.species)
        .subscribe(() => this.goBack());
    }
  }

  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }

nameInputs(){
    var inputs = document.getElementsByTagName("input");
    this.inps = inputs.length;
    for(let i=0; i<inputs.length; i++)
      inputs[i].name = "info" + (i);
}

createNewElement() {
  // First create a DIV element.
  var txtNewInputBox = document.createElement('div');
  txtNewInputBox.className = 'dynamic-class';

  // Then add the content (a new input box) of the element.
  txtNewInputBox.innerHTML = "<input id='newInputBox' class='dynamic-input'>";

  // Finally put it where it is supposed to appear.
  document.getElementById("info")?.appendChild(txtNewInputBox);
}
}

