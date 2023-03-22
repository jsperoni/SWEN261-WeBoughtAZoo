import { Component } from '@angular/core';

import { Customer } from '../customer';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  customers: Customer[] = [];
  loginId: number = 0;

  constructor(private customerService: CustomerService) { }

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
    // this comment adds a new customer, login will not do this, however I do not know how to login yet.
    // this.customerService.addCustomer({ username } as Customer)
    // .subscribe(customer => {
    //  this.customers.push(customer);
    //});
  }
}
