import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {mergeAll, mergeMap, Observable, of} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';

import {Customer} from './customer';
import {MessageService} from './message.service';
import {ShoppingCartService} from './shopping-cart.service';
import {Animal} from "./animals";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customerUrl = 'http://localhost:8080/customers';  // URL to web api
  private loginUrl = 'http://localhost:8080/customers/login'; // URL to login endpoint

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private shoppingCartService: ShoppingCartService) { }

  /** GET customers from the server */
  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.customerUrl}/`)
      .pipe(
        tap(_ => this.log('fetched customers')),
        catchError(this.handleError<Customer[]>('getCustomers', []))
      );
  }

  /** GET customer by id. Return `undefined` when id not found */
  getCustomerNo404<Data>(id: number): Observable<Customer> {
    const url = `${this.customerUrl}/?id=${id}`;
    return this.http.get<Customer[]>(url)
      .pipe(
        map(customers => customers[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} customer id=${id}`);
        }),
        catchError(this.handleError<Customer>(`getCustomer id=${id}`))
      );
  }

  /** GET customer by id. Will 404 if id not found */
  getCustomer(id: number): Observable<Customer> {
    const url = `${this.customerUrl}/${id}`;
    return this.http.get<Customer>(url).pipe(
      tap(_ => this.log(`fetched customer id=${id}`)),
      catchError(this.handleError<Customer>(`getCustomer id=${id}`))
    );
  }

  // search cusomers
  searchCustomers(term: string): Observable<Customer[]> {
    if (!term.trim()) {
      // if not search term, return empty customer array.
      return of([]);
    }
    return this.http.get<Customer[]>(`${this.customerUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found customers matching "${term}"`) :
         this.log(`no customers matching "${term}"`)),
      catchError(this.handleError<Customer[]>('searchCustomers', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new customer to the server */
  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.customerUrl, customer, this.httpOptions).pipe(
      tap((newCustomer: Customer) => this.log(`added customer w/ id=${newCustomer.id}`)),
      catchError(this.handleError<Customer>('addCustomer'))
    );
  }

  /** DELETE: delete the customer from the server */
  deleteCustomer(id: number): Observable<Customer> {
    const url = `${this.customerUrl}/${id}`;

    return this.http.delete<Customer>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted customer id=${id}`)),
      catchError(this.handleError<Customer>('deleteCustomer'))
    );
  }

  /** PUT: update the customer on the server */
  updateCustomer(customer: Customer): Observable<any> {
    return this.http.put(this.customerUrl, customer, this.httpOptions).pipe(
      tap(_ => this.log(`updated customer id=${customer.id}`)),
      catchError(this.handleError<any>('updateCustomer'))
    );
  }

  login(username: string): Observable<any> {

    return this.http.get(`${this.loginUrl}?username=${username}&password=empty`).pipe(
      tap(val => {
        this.log(`logged in as customer name=${username}`);
        let customer = val as Customer;
        this.log(`creating new cart for user ${customer.id}`)
        this.shoppingCartService.createShoppingCart(customer.id);
      }),
      catchError(this.handleError<any>('login'))
    );
  }

  addToCustomerHistory(id: number, animalId: number): Observable<any> {
    return this.http.post(`${this.customerUrl}/history/${id}/${animalId}`, null, this.httpOptions).pipe(
      tap(val => {
        this.log(`added animal id=${animalId} to customer id=${id} history`);
      }),
      catchError(this.handleError<any>('addToCustomerHistory'))
    );
  }

  getCustomerHistory(id: number): Observable<any> {
    return this.http.get(`${this.customerUrl}/history/${id}`).pipe(
      map(val => {
        let history = val as number[];
        return history
          .filter((value, index, self) => self.indexOf(value) === index) // remove duplicates
          .filter((value) => value < 9999);
      }),
      tap<any>(val => {
        this.log(`got customer history for customer id=${id}`);
        let history = val as number[];
        this.log(`history contains ${history.length} items`);
      }),
      catchError(this.handleError<any>('getCustomerHistory'))
    );
  }

  getCustomerHistoryAsProducts(id: number): Observable<Animal[]> {
    // Get customer history as array of animal ids
    // Then get the animals from the animal service
    // Then return the animals
    return this.getCustomerHistory(id)
      .pipe(
        map(val => {
          let history = val as number[];
          // toPromise() is deprecated, but that's the only way I can think to do this
          return history.map(id => this.shoppingCartService.getAnimal(id).toPromise() as Promise<Animal>);
        }),
        // Await all promises
        mergeMap(val => Promise.all(val)),
        tap(val => {
          this.log(`got customer history as products for customer id=${id}`);
          let history = val as Animal[];
          this.log(`history contains ${history.length} items`);
        }),
        catchError(this.handleError<any>('getCustomerHistoryAsProducts'))
      );
  }

  getCustomerSpeciesHistory(id: number): Observable<string[]> {
    return this.getCustomerHistoryAsProducts(id)
      .pipe(
        map(val => {
            let history = val as Animal[];
            return history.map(animal => animal.species)
              .filter((value, index, self) => self.indexOf(value) === index);
          }
        )
      )
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

  /** Log a CustomerService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CustomerService: ${message}`);
  }
}
