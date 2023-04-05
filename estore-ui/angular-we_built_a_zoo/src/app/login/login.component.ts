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
  show: boolean=false;

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
    this.show = true;
  }

  getCurrentCustomer(): Customer | undefined {
    return this.customer;
  }

  isNonuser() : boolean {
    return (this.show);
  }}

export { instance };