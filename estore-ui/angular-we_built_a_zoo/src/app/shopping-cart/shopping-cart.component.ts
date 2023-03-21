import { Component, OnInit } from '@angular/core';
import { Animal } from '../animals';
import { AnimalService } from '../animal.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: [ './shopping-cart.component.css' ]
})
export class ShoppingCartComponent implements OnInit {
  animals: Animal[] = [];

  constructor(private animalService: AnimalService) { }

  ngOnInit(): void {
    this.getAnimals();
  }

  getAnimals(): void {
    this.animalService.getAnimals()
      .subscribe(animals => this.animals = animals.slice(1, 5));
  }
}
