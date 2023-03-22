import { Component, OnInit } from '@angular/core';
import { Animal } from '../animals';
import { ShoppingCart } from '../shopping-cart';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: [ './shopping-cart.component.css' ]
})
export class ShoppingCartComponent implements OnInit {
  shoppingCarts: ShoppingCart[] = [];

  constructor(private shoppingCartService: ShoppingCartService) { }

  ngOnInit(): void {
    //this.getShoppingCarts();
  }

  //getShoppingCarts(): void {
    //this.shoppingCartService.getShoppingCarts()
      //.subscribe(shoppingCarts => this.shoppingCarts = shoppingCarts);
  //}
}
