import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common'
import { NavSidebar } from './components/nav-sidebar/nav-sidebar';
import { NavBar } from './components/nav-bar/nav-bar';
import { Contents } from './components/contents/contents';
import { Footer } from './components/footer/footer';
import { Login } from './components/login/login';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, NavSidebar, NavBar, Contents, Footer, Login],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');

  isLoggedIn = false;

  isLoggedInEmit(loggedInEvent: boolean) {
    this.isLoggedIn = loggedInEvent;
  }
}
