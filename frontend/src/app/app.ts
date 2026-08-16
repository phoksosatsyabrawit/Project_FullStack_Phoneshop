import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavSidebar } from './components/nav-sidebar/nav-sidebar';
import { NavBar } from './components/nav-bar/nav-bar';
import { Contents } from './components/contents/contents';
import { Footer } from './components/footer/footer';



@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavSidebar, NavBar, Contents, Footer],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
