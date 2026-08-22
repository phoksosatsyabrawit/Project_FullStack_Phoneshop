import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { BrandService } from '../../../services/brandservice/brand-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-brand-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './brand-form.html',
  styleUrl: './brand-form.css',
})
export class BrandForm {
  private fb = inject(FormBuilder);
  private service = inject(BrandService);
  private snacbar = inject(MatSnackBar);
  isDisabled = false;

  brandForm = this.fb.group({
    name: ['']
  });

  submit() {
    const brand = this.brandForm.value;
    this.service.save(brand).subscribe({
      next: (res) => console.log(res),
      error: (err: Error) => console.error(err)
    });
    this.snacbar.open('Brand created successfully.', 'Close', {
      duration: 2000
    });
    this.isDisabled = true;
  }
}
