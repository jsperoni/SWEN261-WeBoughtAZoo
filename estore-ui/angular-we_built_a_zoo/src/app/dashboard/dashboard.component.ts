import { Component, OnInit } from '@angular/core';
import { Animal } from '../animals';
import { AnimalService } from '../animal.service';
import { instance } from '../login/login.component';
import { ShoppingCartService } from '../shopping-cart.service';
import { ShoppingCart } from '../shopping-cart';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  animals: Animal[] = [];


  constructor(private animalService: AnimalService, private shoppingCartService: ShoppingCartService) {}

  ngOnInit(): void {
    this.getAnimals();
  }

  getAnimals(): void {
    this.animalService.getAnimals()
      .subscribe(animals => this.animals = animals);
  }

  add(name: string, species: string, priceStr: string): void {
    name = name.trim();
    let price = Number(priceStr);
    species = species.trim();
    if (!name || !species || !price) { return; }
    this.animalService.addAnimal({ name, species, price} as Animal)
      .subscribe(animal => {
        this.animals.push(animal);
      });
  }

  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }

}
