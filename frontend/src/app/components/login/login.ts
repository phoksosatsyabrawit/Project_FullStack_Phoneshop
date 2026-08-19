import { Component, inject, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../../services/authservice/auth-service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private service = inject(AuthService);
  private fb = inject(FormBuilder);

  @Output() isLoggedInEvent = new EventEmitter<boolean>();

  loginForm = this.fb.group({
    username: [''],
    password: ['']
  });

  login() {
    let loginData = this.loginForm.value;
    this.service.login(loginData).subscribe({
      next: (res: HttpResponse<any>) => {
        if (res.status == 200) {
          let token = res.headers.get('Authorization');
          localStorage.setItem('token', token!);
          this.isLoggedInEvent.emit(true);
        }
      },
      error: (err) => { console.error(`Login faild. ${err}`) }
    });
  }
}
