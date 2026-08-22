import { Component } from '@angular/core';
import { BrandList } from '../brand/brand-list/brand-list';

@Component({
  selector: 'app-contents',
  imports: [BrandList],
  templateUrl: './contents.html',
  styleUrl: './contents.css',
})
export class Contents { }
