import { ApplicationConfig, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import {
  provideHttpClient,
  withFetch,               // ⬅ можно убрать, если XHR-backend
  withInterceptorsFromDi, // ⬅ ОБЯЗАТЕЛЬНО для класс-интерсепторов
  HTTP_INTERCEPTORS,
} from '@angular/common/http';

import { routes } from './app.routes';
import { AuthInterceptor } from './interceptor/auth-interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideZonelessChangeDetection(),
    provideHttpClient(
      withFetch(),              // опционально
      withInterceptorsFromDi(), // ← тянет все классы из DI
    ),

    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
};
