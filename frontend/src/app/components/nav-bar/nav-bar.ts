import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
})
export class NavBar {
  @Output() logoutEvent = new EventEmitter();

  logout() {
    this.logoutEvent.emit(false);
  }
}
