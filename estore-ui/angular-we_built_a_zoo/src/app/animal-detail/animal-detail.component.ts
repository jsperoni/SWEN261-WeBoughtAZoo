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
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.animalService.getHero(id)
      .subscribe(animal => this.animal = animal);
  }

  goBack(): void {
    this.location.back();
  }
}