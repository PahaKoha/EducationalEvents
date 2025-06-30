import {Component, OnInit} from '@angular/core';
import {UserDashboardService} from '../../service/user-dashboard-service';
import {InfoAboutEventWindowComponent} from '../info-about-event-window-component/info-about-event-window-component';

@Component({
  selector: 'app-user-dashboard',
  imports: [
    InfoAboutEventWindowComponent
  ],
  templateUrl: './user-dashboard.html',
  styleUrl: './user-dashboard.css'
})
export class UserDashboard implements OnInit {
  events: any[] = [];

  constructor(
    private userDashboardService: UserDashboardService,
  ) {
  }

  ngOnInit(): void {
    this.userDashboardService.fetchEvents()
      .subscribe(evts => this.events = evts);
  }
}
