import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Animal } from '../animals';
import { AnimalService } from '../animal.service';

import { instance } from '../login/login.component';
import { ShoppingCartService } from '../shopping-cart.service';

let components: AnimalDetailComponent[] = [];

@Component({
  selector: 'app-animal-detail',
  templateUrl: './animal-detail.component.html',
  styleUrls: [ './animal-detail.component.css' ]
})
export class AnimalDetailComponent implements OnInit {
  animal: Animal | undefined;
  username?: string = instance.customer?.username;

  constructor(
    private route: ActivatedRoute,
    private animalService: AnimalService,
    private shoppingCartService: ShoppingCartService,
    private location: Location
  ) {

  }

  ngOnInit(): void {
    this.getAnimal();
  }

  getAnimal(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.animalService.getAnimal(id)
      .subscribe(animal => this.animal = animal);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.animal) {
      this.animalService.updateAnimal(this.animal)
        .subscribe(() => this.goBack());
    }
  }

  isAdmin() : boolean {
    return instance.customer?.username === 'admin';
  }

  add(): void {
    console.log("attempted to add to shopping cart");
    this.shoppingCartService.addToCart(instance.customer?.id ? instance.customer?.id : 9999, this.animal ? this.animal.id : 9999).subscribe(() => {});
  }

  remove(): void {
    this.shoppingCartService.removeFromCart(instance.customer?.id ? instance.customer?.id: 9999 , this.animal ? this.animal.id : 9999).subscribe(() => {});
  }

  isUser(): boolean {
    if(instance.customer?.username){
      return true;
    } else{
      return false;
    }
  }
}