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
  isLoggedIn = signal(false);

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.authService.refreshSession().subscribe({
        next: () => { this.isLoggedIn.set(true) },
        error: () => { this.isLoggedIn.set(false) }
      })
    }
  }

  isLoggedInEmit(loggedInEvent: boolean) {
    this.isLoggedIn.set(loggedInEvent);
    console.log(loggedInEvent)
  }

  isLoggedOutEmit(loggedoutEvent: boolean) {
    this.isLoggedIn.set(loggedoutEvent);
  }
}
