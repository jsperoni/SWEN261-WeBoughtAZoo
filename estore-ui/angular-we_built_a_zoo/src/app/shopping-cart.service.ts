import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, filter, map, tap } from 'rxjs/operators';

import { Animal } from './animals';
import { ShoppingCart } from './shopping-cart';
import { MessageService } from './message.service';
import {CustomerService} from "./customer.service";

@Injectable({ providedIn: 'root' })
export class ShoppingCartService {

  private shoppingCartUrl = 'http://localhost:8080/shoppingcart';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET shopping carts from the server */
  getShoppingCarts(): Observable<ShoppingCart[]> {
  return this.http.get<ShoppingCart[]>(this.shoppingCartUrl)
    .pipe(
      tap(val => this.log(`fetched shopping carts (got ${val.length} carts)`)),
      tap(val => {
        for(let cart of val) {
          this.log(`got ${cart.customer_id}`)
        }
      }),
      catchError(this.handleError<ShoppingCart[]>('getShoppingCarts', []))
    );
  }

  /** GET shopping cart by id. Return `undefined` when id not found */
  getShoppingCartNo404<Data>(id: number): Observable<ShoppingCart> {
    const url = `${this.shoppingCartUrl}/${id}`;
    return this.http.get<ShoppingCart[]>(url)
      .pipe(
        map(shoppingCarts => shoppingCarts[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} shopping cart id=${id}`);
        }),
        catchError(this.handleError<ShoppingCart>(`getShoppingCart id=${id}`))
      );
  }

  /** GET shopping cart by id. Will 404 if id is not found */
  getShoppingCart(id: number): Observable<ShoppingCart> {
    const url = `${this.shoppingCartUrl}`;
    this.log(`attempting to get ${url}`);
    return this.http.get<ShoppingCart[]>(url).pipe(
      map(carts => {
        return carts
        .find(cart => cart.customer_id == id) as ShoppingCart
      }),
      tap(val => this.log(`fetched shopping cart id=${id} (contains ${val.animals.length} animals)`)),
      catchError(this.handleError<ShoppingCart>(`getShoppingCart id=${id}`))
      );
  }

  /** DELETE: delete the shopping cart from the server */
  deleteShoppingCart(id: number): Observable<ShoppingCart> {
    const url = `${this.shoppingCartUrl}/${id}`;

    return this.http.delete<ShoppingCart>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted shopping cart id=${id}`)),
      catchError(this.handleError<ShoppingCart>('deleteShoppingCart'))
    );
  }

  /** PUT: update the shopping cart on the server */
  updateShoppingCart(shoppingCart: ShoppingCart): Observable<any> {
    return this.http.put(this.shoppingCartUrl, shoppingCart, this.httpOptions).pipe(
      tap(_ => this.log(`updated shopping cart id=${shoppingCart.customer_id}`)),
      catchError(this.handleError<any>('updateShoppingCart'))
    );
  }

  getAnimal(id: number): Observable<any> {
    return this.http.get(`http://localhost:8080/animals/${id}`).pipe(
      tap(_ => this.log(`fetched animal id=${id}`)),
      catchError(this.handleError<any>('getAnimal'))
    );
  }

  addToCart(cartId: number, animalId: number): Observable<any> {
    return this.http.put(`${this.shoppingCartUrl}/${cartId}/${animalId}`, "nothing goes here").pipe(
      tap(_ => this.log(`added animal id=${animalId} to cart id=${cartId}`)),
      catchError(this.handleError<any>('addToCart'))
    );
  }

  removeFromCart(cartId: number, animalId: number): Observable<any> {
    return this.http.delete(`${this.shoppingCartUrl}/${cartId}/${animalId}`).pipe(
      tap(_ => this.log(`removed animal id=${animalId} from cart id=${cartId}`)),
      catchError(this.handleError<any>('removeFromCart'))
    );
  }

  createShoppingCart(cartId: number) {
    return this.addToCart(cartId, 9999).subscribe(
      _ => this.removeFromCart(cartId, 9999));
  }

  checkout(cartId: number): Observable<any> {
    // add all animals in cart to customer history
    this.getShoppingCart(cartId).subscribe(cart => {
      for(let animal of cart.animals) {
        this.addToCustomerHistory(cart.customer_id, animal).subscribe();
      }
    })

    return this.http.post(`${this.shoppingCartUrl}/checkout/${cartId}`, "nothing goes here").pipe(
      tap(_ => this.log(`checked out cart id=${cartId}`)),
      catchError(this.handleError<any>('checkout'))
    );
  }

  private addToCustomerHistory(customerId: number, animal: number): Observable<any> {
    return this.http.get(`http://localhost:8080/customers/history/${customerId}/${animal}`).pipe(
      tap(_ => this.log(`added animal id=${animal} to customer id=${customerId}`)),
      catchError(this.handleError<any>('addToCustomerHistory'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ShoppingCartService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ShoppingCartService: ${message}`);
  }
}
