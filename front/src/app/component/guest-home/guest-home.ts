import {LogInService} from '../../service/log-in-service';
import {RegistrationService} from '../../service/registration-service';
import {Component} from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-guest-home',
  template: `
    <section class="hero">
      <div class="hero__content">
        <h1 class="hero__title">ITMO Education&nbsp;Events</h1>
        <p class="hero__lead">
          Расписание мастер-классов, встреч и онлайн-курсов.
          <br />
          Чтобы зарегистрироваться, <a (click)="openLogin()">войдите</a> или
          <a (click)="openReg()">создайте аккаунт</a>.
        </p>

        <div class="hero__cta">
          <button class="btn" (click)="openLogin()">Войти</button>
          <button class="btn btn--ghost" (click)="openReg()">Регистрация</button>
        </div>
      </div>

      <div class="hero__art">
        <img src="" alt="" />
      </div>
    </section>
  `,
  styleUrl: './guest-home.css'
})
export class GuestHomeComponent {
  constructor(
    private loginDialog: LogInService,
    private regDialog: RegistrationService
  ) {}

  openLogin() { this.loginDialog.changeWindowState(); }
  openReg()   { this.regDialog.changeWindowState(); }
}
