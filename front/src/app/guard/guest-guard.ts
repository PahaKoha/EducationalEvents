import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '../service/auth-service';
import {inject} from '@angular/core';

export const guestGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.token) {
    return true;
  }
  return router.parseUrl('/dashboard');
};
