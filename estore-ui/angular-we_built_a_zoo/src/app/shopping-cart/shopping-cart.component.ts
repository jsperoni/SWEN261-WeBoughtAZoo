import { Component, OnInit } from '@angular/core';
import { Animal } from '../animals';
import { ShoppingCart } from '../shopping-cart';
import { ShoppingCartService } from '../shopping-cart.service';
import { instance } from '../login/login.component'
import { Customer } from '../customer';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: [ './shopping-cart.component.css' ]
})
export class ShoppingCartComponent implements OnInit {
  shoppingCarts: ShoppingCart[] = [];
  shoppingCart?: ShoppingCart;
  animals: Animal[] = [];

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    this.getShoppingCarts();
  }

  getShoppingCarts(): void {
    this.shoppingCartService.getShoppingCarts()
      .subscribe(shoppingCarts => this.shoppingCarts = shoppingCarts);
  }

  getShoppingCart(): void {
    this.shoppingCartService.getShoppingCart(((instance.getCurrentCustomer() as Customer).id))
      .subscribe(shoppingCart => this.shoppingCart = shoppingCart);
  }

  getAnimals(): void {
    if (!this.shoppingCart) {
        this.animals = [];
        return;
    }
    for (let id of (this.shoppingCart as ShoppingCart).animals.values() ) {
      this.shoppingCartService.getAnimal(id)
        .subscribe(animals => this.animals = animals);
    }
  }
}
