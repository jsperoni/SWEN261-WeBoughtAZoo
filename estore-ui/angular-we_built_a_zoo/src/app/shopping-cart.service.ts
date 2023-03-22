import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Animal } from './animals';
import { ShoppingCart } from './shopping-cart';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class ShoppingCartService {

  private shoppingCartUrl = 'http://localhost:8080/shoppingcart';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

}
