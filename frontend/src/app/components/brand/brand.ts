import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { BrandService } from '../../services/brandservice/brand-service';

@Component({
  selector: 'app-brand',
  imports: [ReactiveFormsModule],
  templateUrl: './brand.html',
  styleUrl: './brand.css',
})
export class Brand {
  private fb = inject(FormBuilder);
  private service = inject(BrandService);

  brandForm = this.fb.group({
    name: ['']
  });

  submit() {
    const brand = this.brandForm.value;
    this.service.save(brand).subscribe({
      next: (res) => console.log(res),
      error: (err: Error) => console.error(err)
    });
  }
}
