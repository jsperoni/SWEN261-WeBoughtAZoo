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

  /** GET shopping carts from the server */
  getShoppingCarts(): Observable<ShoppingCart[]> {
  return this.http.get<ShoppingCart[]>(this.shoppingCartUrl)
    .pipe(
      tap(_ => this.log('fetched shopping carts')),
      catchError(this.handleError<ShoppingCart[]>('getShoppingCarts', []))
    );
  }

  /** GET shopping cart by id. Return `undefined` when id not found */
  getShoppingCartNo404<Data>(id: number): Observable<ShoppingCart> {
    const url = `${this.shoppingCartUrl}/?id=${id}`;
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
  
  /** GET shopping cart by id. Will 404 if id not found */
  getShoppingCart(id: number): Observable<ShoppingCart> {
    const url = `${this.shoppingCartUrl}/${id}`;
    return this.http.get<ShoppingCart>(url).pipe(
      tap(_ => this.log(`fetched shopping cart id=${id}`)),
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
      tap(_ => this.log(`updated shopping cart id=${shoppingCart.id}`)),
      catchError(this.handleError<any>('updateShoppingCart'))
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
