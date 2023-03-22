import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Animal } from '../animals';
import { AnimalService } from '../animal.service';

@Component({
  selector: 'app-animal-detail',
  templateUrl: './animal-detail.component.html',
  styleUrls: [ './animal-detail.component.css' ]
})
export class AnimalDetailComponent implements OnInit {
  animal: Animal | undefined;

  constructor(
    private route: ActivatedRoute,
    private animalService: AnimalService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getAnimal();
  }

  getAnimal(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.animalService.getAnimal(id)
      .subscribe(animal => this.animal = animal);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.animal) {
      this.animalService.updateAnimal(this.animal)
        .subscribe(() => this.goBack());
    }
  }
}