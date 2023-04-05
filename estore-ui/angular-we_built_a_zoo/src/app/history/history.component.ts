import {Component, OnInit} from '@angular/core';

import { CustomerService } from '../customer.service';
import {Animal} from "../animals";
import { instance } from '../login/login.component'
import {Customer} from "../customer";
import { AnimalService } from '../animal.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  animals: Animal[] = [];
  sample: Animal[] = [];

  ngOnInit(): void {
    this.getHistory();
    this.getAnimals();
  }

  constructor(private CustomerService: CustomerService, private AnimalService: AnimalService) { }

  getHistory(): void {
    console.log(`Customer ID for History: ${instance.getCurrentCustomer() ? (instance.getCurrentCustomer() as Customer).id : 9999}`);
    this.CustomerService.getCustomerHistoryAsProducts(
      instance.getCurrentCustomer() ? (instance.getCurrentCustomer() as Customer).id : 9999
    ).subscribe(animals => {
      this.animals = animals;
      console.log(`History Animals: ${this.animals.length}`);
      for (let animal of this.animals) {
        console.log(`${animal.id} ${animal.name} ${animal.species} ${animal.price}`);
      }
    });
  }

  getAnimals(): void {
    this.AnimalService.getAnimals()
      .subscribe(animals => this.sample = animals);
  }

  isUser(): boolean {
    if(instance.customer?.username){
      return true;
    } else{
      return false;
    }
  }

  isAdmin(): boolean {
    if(instance.customer?.username == "admin" ){
      return true;
    } else {
      return false;
    }
  }
}
