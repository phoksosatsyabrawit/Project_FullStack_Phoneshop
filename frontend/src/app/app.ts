import { Component, signal, OnInit, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common'
import { NavSidebar } from './components/nav-sidebar/nav-sidebar';
import { NavBar } from './components/nav-bar/nav-bar';
import { Contents } from './components/contents/contents';
import { Footer } from './components/footer/footer';
import { Login } from './components/login/login';
import { AuthService } from './services/authservice/auth-service';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, NavSidebar, NavBar, Contents, Footer, Login],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');
  private platformId = inject(PLATFORM_ID);
  private authService = inject(AuthService);
  isLoggedInCall = this.authService.isLoggedIn;

  ngOnInit(): void {
  }

  isLoggedInEmit(loggedInEvent: boolean) {
    this.isLoggedInCall.set(loggedInEvent);
  }

  isLoggedOutEmit(loggedoutEvent: boolean) {
    this.isLoggedInCall.set(loggedoutEvent);
  }
}
