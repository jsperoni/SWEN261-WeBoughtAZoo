import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AnimalDetailComponent } from './animal-detail/animal-detail.component';
import { AnimalsComponent } from './animals/animals.component';
import { AnimalSearchComponent } from './animal-search/animal-search.component';
import { MessagesComponent } from './messages/messages.component';
import { RouterModule } from '@angular/router';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { LoginComponent } from './login/login.component';
import { HistoryComponent } from './history/history.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    AnimalsComponent,
    AnimalDetailComponent,
    MessagesComponent,
    AnimalSearchComponent,
    ShoppingCartComponent,
    LoginComponent,
    HistoryComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }