import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavBar} from './component/nav-bar/nav-bar';
import {LogInService} from './service/log-in-service';
import {RegistrationService} from './service/registration-service';
import {LogInComponent} from './component/log-in-component/log-in-component';
import {RegistrationComponent} from './component/registration-component/registration-component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LogInComponent,
    RegistrationComponent, NavBar],
  standalone: true,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'front';

  constructor(private logInService: LogInService, private registrationService: RegistrationService) {
  }

  isLogInWindowOpen(): boolean {
    return this.logInService.isWindowOpen();
  }

  isRegistrationWindowOpen(): boolean {
    return this.registrationService.isWindowOpen();
  }
}
