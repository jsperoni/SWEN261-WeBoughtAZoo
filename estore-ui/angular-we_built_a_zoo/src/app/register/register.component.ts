import { Component } from '@angular/core';

import { Customer } from '../customer';
import { CustomerService } from '../customer.service';

let instance: RegisterComponent;

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  customers: Customer[] = [];
  customer?: Customer;
  show: boolean= false;
  register: boolean=false;

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
    this.register=true;
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
    return (this.register);
  }
}

export { instance };
