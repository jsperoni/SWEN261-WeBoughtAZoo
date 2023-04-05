import { Component } from '@angular/core';
import { instance } from './login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'We Bought a Zoo';
  user = 'User1';

  getUsername(): string {
    if(instance.customer?.username){
      return instance.customer.username;
    }
    else{
      return "Guest";
    }
  }

  loggedIn(): boolean {
    return !!instance?.customer?.username;
  }
}
