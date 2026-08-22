import { Component, inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormArray } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/authservice/auth-service';

@Component({
  selector: 'app-user',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user.html',
  styleUrl: './user.css',
})
export class User implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(AuthService);
  isSubmited = false;

  roleNames = ["SALE", "FINANCE", "HR"];

  get roles() {
    return this.userForm.get("roles") as FormArray;
  }

  userForm = this.fb.group({
    username: [''],
    email: [''],
    password: [''],
    roles: this.fb.array([])
  });

  ngOnInit(): void {
    for (let i of this.roleNames) {
      this.roles.push(this.fb.control(''));
    }
  }

  private userData() {
    let data = this.userForm.value;
    let role = data.roles ?? [];
    let selectedRole = [];
    for (let i = 0; i < role.length; i++) {
      if (role[i]) {
        selectedRole.push(this.roleNames[i]);
      }
    }
    data.roles = selectedRole;
    return data;
  }

  submit() {
    this.isSubmited = true;
    const user = this.userData();
    this.service.save(user).subscribe({
      next: (res) => { res.status(200).send(res) },
      error: (err: Error) => { console.error(err) }
    });
  }
}