import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Species } from './species';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class SpeciesService {

  private speciesUrl = 'http://localhost:8080/Species';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET species from the server */
  getSpeciess(): Observable<Species[]> {
    return this.http.get<Species[]>(this.speciesUrl)
      .pipe(
        tap(_ => this.log('fetched species')),
        catchError(this.handleError<Species[]>('getSpeciess', []))
      );
  }

  /** GET species by name. Return `undefined` when id not found */
  getSpeciesNo404<Data>(name: string): Observable<Species> {
    const url = `${this.speciesUrl}/${name}`;
    return this.http.get<Species[]>(url)
      .pipe(
        map(species => species[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} species name=${name}`);
        }),
        catchError(this.handleError<Species>(`getSpecies name=${name}`))
      );
  }

  /** GET species by name. Will 404 if id not found */
  getSpecies(name: string): Observable<Species> {
    const url = `${this.speciesUrl}/${name}`;
    return this.http.get<Species>(url).pipe(
      tap(_ => this.log(`fetched species name=${name}`)),
      catchError(this.handleError<Species>(`getSpecies name=${name}`))
    );
  }

  // search species
  searchSpecies(term: string): Observable<Species[]> {
    if (!term.trim()) {
      // if not search term, return empty species array.
      return of([]);
    }
    return this.http.get<Species[]>(`${this.speciesUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found species matching "${term}"`) :
         this.log(`no species matching "${term}"`)),
      catchError(this.handleError<Species[]>('searchSpecies', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new species to the server */
  addSpecies(species: Species): Observable<Species> {
    console.log("Tried to add a species (in the service)");
    return this.http.post<Species>(this.speciesUrl, species, this.httpOptions).pipe(
      tap((newSpecies: Species) => this.log(`added species w/ name=${newSpecies.name}`)),
      catchError(this.handleError<Species>('addSpecies'))
    );
  }

  /** DELETE: delete the species from the server */
  deleteSpecies(name: string): Observable<Species> {
    const url = `${this.speciesUrl}/${name}`;

    return this.http.delete<Species>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted species name=${name}`)),
      catchError(this.handleError<Species>('deleteSpecies'))
    );
  }

  /** PUT: update the species on the server */
  updateSpecies(species: Species): Observable<any> {
    console.log(species.info);
    return this.http.put(this.speciesUrl, species, this.httpOptions).pipe(
      tap(_ => this.log(`updated species name=${species.name}`)),
      catchError(this.handleError<any>('updateSpecies'))
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

  /** Log a SpeciesService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`SpeciesService: ${message}`);
  }
}