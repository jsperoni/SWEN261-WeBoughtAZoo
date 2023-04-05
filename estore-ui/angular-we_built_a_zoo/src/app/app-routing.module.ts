import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AnimalsComponent } from './animals/animals.component';
import { AnimalDetailComponent } from './animal-detail/animal-detail.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { LoginComponent } from './login/login.component';
import { HistoryComponent } from "./history/history.component";
import { SpeciesComponent } from './species/species.component';
import { SpeciesDetailComponent } from './species-detail/species-detail.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'dashboard', component: DashboardComponent },
  { path: 'detail/:id', component: AnimalDetailComponent },
  { path: 'animals', component: AnimalsComponent },
  { path: 'history', component: HistoryComponent },
  { path: 'shoppingCart', component: ShoppingCartComponent }, 
  { path: 'species', component: SpeciesComponent }, 
  { path: 'species-detail/:name', component: SpeciesDetailComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
