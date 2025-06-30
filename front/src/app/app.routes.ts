import {Routes} from '@angular/router';
import {GuestHomeComponent} from './component/guest-home/guest-home';
import {UserDashboard} from './component/user-dashboard/user-dashboard';
import {authGuard} from './guard/auth-guard';
import {guestGuard} from './guard/guest-guard';

export const routes: Routes = [
  {
    path: '',
    component: GuestHomeComponent,
    canActivate: [guestGuard],
  },
  {
    path: 'dashboard',
    component: UserDashboard,
    canActivate: [authGuard],
    children: [
      // {path: '', component: EventListComponent},
    ]
  },

  {path: '**', redirectTo: ''},
];
