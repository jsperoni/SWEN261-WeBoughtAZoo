import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Animal } from './animals';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const animals = [
      { id: 12, name: 'Tiger' },
      { id: 13, name: 'Giraffe' },
      { id: 14, name: 'Monkey' },
      { id: 15, name: 'Spider' },
      { id: 16, name: 'Cricket' },
      { id: 17, name: 'Cat' },
      { id: 18, name: 'Bear' },
      { id: 19, name: 'Eagle' },
      { id: 20, name: 'Wolf' }
    ];
    return {animals};
  }

  genId(animals: Animal[]): number {
    return animals.length > 0 ? Math.max(...animals.map(animals => animals.id)) + 1 : 11;
  }
}
