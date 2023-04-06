import { Component, OnInit } from '@angular/core';
import { Animal } from '../animals';
import { ShoppingCart } from '../shopping-cart';
import { ShoppingCartService } from '../shopping-cart.service';
import { instance } from '../login/login.component'
import { Customer } from '../customer';
import { AnimalService } from '../animal.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: [ './shopping-cart.component.css' ]
})
export class ShoppingCartComponent implements OnInit {
  shoppingCarts: ShoppingCart[] = [];
  shoppingCart?: ShoppingCart;
  animals: Animal[] = [];

  constructor(private shoppingCartService: ShoppingCartService,
    private animalSerivce: AnimalService, public router: Router) {}

  ngOnInit(): void {
    this.getShoppingCarts();
    this.getShoppingCart();
  }

  getShoppingCarts(): void {
    this.shoppingCartService.getShoppingCarts()
      .subscribe(shoppingCarts => this.shoppingCarts = shoppingCarts);
  }

  getShoppingCart(): void {
    this.shoppingCartService.getShoppingCart(((instance.getCurrentCustomer() as Customer).id))
      .subscribe(shoppingCart => this.updateCart(shoppingCart));
  }

  getAnimals(): void {
    if (!this.shoppingCart) {
        this.animals = [];
        return;
    }
    for (let id of (this.shoppingCart as ShoppingCart).animals.values() ) {
      this.animalSerivce.getAnimal(id)
      .subscribe(animal => {
        console.log("Adding animal to internal animals list");
        let added: Boolean = false;
        for (let a of this.animals) {
          if (a === animal) {
            added = true;
          }
        }
        if (!added && animal.id < 9999) { // prevents adding the fake 9999 animal
          console.log("Actually added");
          this.animals.push(animal);
        }
      })
    }
  }

  add(id: number): void {
    this.shoppingCartService.addToCart(this.shoppingCart?.customer_id ? this.shoppingCart.customer_id : 9999, id)
      .subscribe((value) => this.updateCart(value as ShoppingCart));
  }

  remove(id: number): void {
    this.shoppingCartService.removeFromCart(this.shoppingCart?.customer_id ? this.shoppingCart.customer_id : 9999, id)
      .subscribe((value) => this.updateCart(value as ShoppingCart));
  }

  updateCart(newCart: ShoppingCart) {
    // console.log(`Got new shopping cart: ${newCart}`);
    this.shoppingCart = newCart;
    this.getAnimals();
  }

  async checkout(): Promise<void> {
    (await this.shoppingCartService.checkout(this.shoppingCart?.customer_id ? this.shoppingCart.customer_id : 9999))
      .subscribe((value) => this.updateCart(value as ShoppingCart));
  }

  isUser(): boolean {
    if(instance.customer?.username){
      return true;
    } else{
      return false;
    }
  }

  isAdmin(): boolean {
    if(instance.customer?.username === "admin" ){
      return true;
    } else {
      return false;
    }
  }
}
