import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LogInService {

  private windowState = false;

  changeWindowState() {
    this.windowState = !this.windowState;
  }

  isWindowOpen() {
    return this.windowState;
  }
}
