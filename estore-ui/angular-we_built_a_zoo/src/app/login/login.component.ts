import { Component } from '@angular/core';

import { Customer } from '../customer';
import { CustomerService } from '../customer.service';

let instance: LoginComponent;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  customers: Customer[] = [];
  customer?: Customer;

  constructor(private customerService: CustomerService) {
    instance = this;
   }

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers(): void {
    this.customerService.getCustomers()
    .subscribe(customers => this.customers = customers);
  }

  add(username: string): void {
    if (!username) { return; }
    this.customerService.addCustomer({ username } as Customer)
      .subscribe(customer => {
        this.customers.push(customer);
      });
  }

  login(username: string): void{
    if (!username) { return; }
    this.customerService.login(username)
      .subscribe(customer => this.customer = customer);
  }

  getCurrentCustomer(): Customer | undefined {
    return this.customer;
  }
}

export { instance };