import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Animal } from './animals';
import { ANIMALS } from './mock-animals';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class AnimalService {

  constructor(private messageService: MessageService) { }

  getAnimals(): Observable<Animal[]> {
    const animals = of(ANIMALS);
    this.messageService.add('AnimalService: fetched heroes');
    return animals;
  }

  getHero(id: number): Observable<Animal> {
    // For now, assume that a hero with the specified `id` always exists.
    // Error handling will be added in the next step of the tutorial.
    const animal = ANIMALS.find(h => h.id === id)!;
    this.messageService.add(`Animal service: fetched animal id=${id}`);
    return of(animal);
  }
}