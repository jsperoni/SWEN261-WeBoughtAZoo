import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  login(username: string): void{
    if (!username) { return; }
    //do the backend stuff here with format like:
    //this.animalService.addAnimal({ name } as Animal)
    //.subscribe(animal => {
    //  this.animals.push(animal);
    //});
  }
}
