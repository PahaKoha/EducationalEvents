import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { LogInService } from '../../service/log-in-service';
import { Subscription } from 'rxjs';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css',
  standalone: true,
  imports: [
    RouterLink
  ]
})
export class NavBar implements OnInit, OnDestroy {
  username: string | null = null;
  private sub?: Subscription;      // чтобы отписаться

  constructor(
    private auth: AuthService,
    private loginDialog: LogInService
  ) {}

  ngOnInit() {
    this.sub = this.auth.username$.subscribe(
      (u: string | null) => (this.username = u)
    );
  }

  ngOnDestroy() {
    this.sub?.unsubscribe();
  }

  changeLogInWindowState(): void {
    this.loginDialog.changeWindowState();
  }

  logout(): void {
    this.auth.logout();
  }
}
