import { Component, Input } from '@angular/core';
import { Animal } from '../animals';

@Component({
  selector: 'app-animal-detail',
  templateUrl: './animal-detail.component.html',
  styleUrls: ['./animal-detail.component.css']
})
export class AnimalDetailComponent {
  @Input() animal?: Animal;
}