import { Component, OnInit } from '@angular/core';

import { Animal } from '../animals';
import { AnimalService } from '../animal.service';

@Component({
  selector: 'app-animals',
  templateUrl: './animals.component.html',
  styleUrls: ['./animals.component.css']
})
export class AnimalsComponent implements OnInit {
  animals: Animal[] = [];

  constructor(private animalService: AnimalService) { }

  ngOnInit(): void {
    this.getAnimals();
  }

  getAnimals(): void {
    this.animalService.getAnimals()
    .subscribe(animals => this.animals = animals);
  }
  add(name: string, species: string, price: number): void {
    name = name.trim();
    species = species.trim();
    if (!name || !species || !price) { return; }
    this.animalService.addAnimal({ name, species, price} as Animal)
      .subscribe(animal => {
        this.animals.push(animal);
      });
  }

  delete(animal: Animal): void {
    this.animals = this.animals.filter(h => h !== animal);
    this.animalService.deleteAnimal(animal.id).subscribe();
  }

}