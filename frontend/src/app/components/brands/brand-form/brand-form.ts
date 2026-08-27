import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { BrandService } from '../../../services/brandservice/brand-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterLink, ActivatedRoute, ParamMap } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-brand-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './brand-form.html',
  styleUrl: './brand-form.css',
})
export class BrandForm implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(BrandService);
  private snacbar = inject(MatSnackBar);
  private actRoute = inject(ActivatedRoute);
  isDisabled = false;
  brandId!: string;

  brandForm = this.fb.group({
    id: [''],
    name: ['']
  });

  ngOnInit(): void {
    this.activateRoute();
  }

  private activateRoute() {
    this.actRoute.paramMap.subscribe((pm: ParamMap) => {
      this.brandId = pm.get('id')!;
      if (this.brandId) {
        this.service.getById(this.brandId).subscribe({
          next: (res) => {
            this.brandForm.patchValue({
              id: res.id,
              name: res.name
            });
          },
          error: (err) => {
            console.error(err);
          }
        });
      }
    });
  }

  submit() {
    this.actRoute.paramMap.subscribe((pm: ParamMap) => {
      this.brandId = pm.get('id')!;
    })
    if (this.brandId) {
      this.update();
    } else {
      this.create();
    }
    this.isDisabled = true;
  }

  create() {
    const brand = this.brandForm.value;
    this.service.save(brand).subscribe({
      next: () => {
        this.snacbar.open('Brand created successfully.', 'Close', {
          duration: 3000
        });
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 403) {
          this.snacbar.open('[Unauthorized: You do not have right permission.]', 'Close', {
            duration: 3000
          });
        }
      }
    });
  }

  update() {
    let brand = this.brandForm.value;
    this.service.update(brand).subscribe({
      next: () => {
        this.snacbar.open('Brand updated successfully.', 'Close', { duration: 3000 });
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 401) {
          this.snacbar.open('[Unauthorized: You do not have right permission.]', 'Close', { duration: 3000 });
        }
      }
    });
  }
}
