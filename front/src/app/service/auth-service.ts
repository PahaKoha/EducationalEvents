import {inject, Injectable, PLATFORM_ID} from "@angular/core";
import {isPlatformBrowser} from "@angular/common";
import {BehaviorSubject, catchError, EMPTY, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {TokenPair} from "../transfer/token-pair";
import {environment} from "../../env/environment-local";

function decodeJwt<T = unknown>(token: string): T | null {
  try {
    return JSON.parse(atob(token.split(".")[1] ?? ""));
  } catch {
    return null;
  }
}

@Injectable({ providedIn: "root" })
export class AuthService {
  private readonly tokenKey = "access";
  private readonly userKey = "u";

  private access: string | null = null;
  private refreshInFlight = false;

  private user$ = new BehaviorSubject<string | null>(null);
  readonly username$ = this.user$.asObservable();

  private readonly isBrowser = isPlatformBrowser(inject(PLATFORM_ID));

  constructor(private http: HttpClient) {
    if (this.isBrowser) {
      const token = sessionStorage.getItem(this.tokenKey);
      const uname = sessionStorage.getItem(this.userKey);
      if (token && this.tokenNotExpired(token)) {
        this.access = token;
        this.user$.next(uname);
      }
    }
  }

  /** Текущий access‑токен, если ещё жив */
  get token(): string | null {
    return this.access && this.tokenNotExpired(this.access) ? this.access : null;
  }

  // ───── public API ────────────────────────────────────────── //

  login(dto: { username: string; password: string }) {
    return this.http
      .post<TokenPair>(`${environment.backendUrl}/user/authorization`, dto, {
        withCredentials: true,
      })
      .pipe(tap(({ access, username }) => this.setSession(access, username)));
  }

  silentRefresh() {
    if (this.refreshInFlight) return EMPTY;
    this.refreshInFlight = true;

    return this.http
      .post<TokenPair>(`${environment.backendUrl}/user/refresh`, {}, {
        withCredentials: true,
      })
      .pipe(
        tap(({ access, username }) => this.setSession(access, username)),
        catchError(() => {
          this.logoutSilent();
          return EMPTY;
        }),
        tap(() => (this.refreshInFlight = false))
      );
  }

  logout() {
    this.http
      .post(`${environment.backendUrl}/user/logout`, {}, {
        withCredentials: true,
      })
      .subscribe({ complete: () => this.logoutSilent() });
  }

  // ───── internal helpers ───────────────────────────────────── //

  private setSession(access: string, username: string) {
    this.access = access;
    this.user$.next(username);

    if (this.isBrowser) {
      sessionStorage.setItem(this.tokenKey, access);
      sessionStorage.setItem(this.userKey, username);
    }
  }

  logoutSilent() {
    this.access = null;
    this.user$.next(null);

    if (this.isBrowser) {
      sessionStorage.removeItem(this.tokenKey);
      sessionStorage.removeItem(this.userKey);
    }
  }

  private tokenNotExpired(token: string): boolean {
    const payload = decodeJwt<{ exp: number }>(token);
    return !!(payload && payload.exp * 1000 > Date.now());
  }
}
