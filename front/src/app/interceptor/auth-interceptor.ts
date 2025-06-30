import { Injectable, inject } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
  HTTP_INTERCEPTORS,
} from "@angular/common/http";
import { Observable, catchError, switchMap, throwError } from "rxjs";
import { AuthService } from "../service/auth-service";
import { environment } from "../../env/environment-local";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly auth = inject(AuthService);
  private readonly refreshUrl = `${environment.backendUrl}/user/refresh`;

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (req.url === this.refreshUrl) {
      return next.handle(req.clone({ withCredentials: true }));
    }

    const access = this.auth.token;
    let authReq = access ? req.clone({ setHeaders: { Authorization: `Bearer ${access}` } }) : req;

    if (!authReq.withCredentials) {
      authReq = authReq.clone({ withCredentials: true });
    }

    return next.handle(authReq).pipe(
      catchError(err => {
        if (!(err instanceof HttpErrorResponse) || err.status !== 401) {
          return throwError(() => err);
        }

        return this.auth.silentRefresh().pipe(
          switchMap(() => {
            const newToken = this.auth.token;
            if (!newToken) return throwError(() => err);

            const retry = req.clone({
              setHeaders: { Authorization: `Bearer ${newToken}` },
              withCredentials: true,
            });
            return next.handle(retry);
          }),
          catchError(e => {
            this.auth.logoutSilent();
            return throwError(() => e);
          })
        );
      })
    );
  }
}
