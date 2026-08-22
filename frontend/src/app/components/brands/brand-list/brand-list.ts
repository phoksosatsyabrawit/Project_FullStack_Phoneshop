import { Component, OnInit, signal } from '@angular/core';
import { BrandService } from '../../../services/brandservice/brand-service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-brand-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './brand-list.html',
  styleUrl: './brand-list.css',
})
export class BrandList implements OnInit {

  brandList = signal<any[]>([]);

  constructor(
    private brandService: BrandService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getBrand();
  }

  getBrand() {
    this.brandService.getBrand().subscribe({
      next: (res) => {
        this.brandList.set(res.page);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  private getBrandsList(param: HttpParams) {
    this.brandService.getBrands(param).subscribe({
      next: (res) => {
        this.brandList.set(res.page);
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  loadBrandByLimit(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const limit = select.value;
    const param = new HttpParams().append('_limit', limit);
    this.getBrandsList(param);
  }

  redirectTo() {
    this.router.navigate(['/brand/form']);
  }
}
