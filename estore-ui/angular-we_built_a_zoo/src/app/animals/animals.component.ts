import { Component } from '@angular/core';
import { Animal } from '../animals';
import { ANIMALS } from '../mock-animals';

@Component({
  selector: 'app-animals',
  templateUrl: './animals.component.html',
  styleUrls: ['./animals.component.css']
})

export class AnimalsComponent {

  animals = ANIMALS;
  selectedAnimal?: Animal;

  onSelect(animals: Animal): void {
    this.selectedAnimal = animals;
  }
}