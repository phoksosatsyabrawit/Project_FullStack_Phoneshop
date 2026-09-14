import { Component, Output, EventEmitter, inject } from '@angular/core';
import { AuthService } from '../../services/authservice/auth-service'

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
})
export class NavBar {
  private authService = inject(AuthService);
  @Output() logoutEvent = new EventEmitter();

  logout() {
    this.authService.isLoggedIn.set(true);
    this.logoutEvent.emit(true);
  }
}
