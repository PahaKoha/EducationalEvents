import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {RegistrationUserTO} from '../transfer/registration-to';
import {environment} from '../../env/environment-local';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private windowState: boolean = false;

  constructor(private httpClient: HttpClient) {
  }

  isWindowOpen(): boolean {
    return this.windowState;
  }

  changeWindowState(): void {
    this.windowState = !this.windowState;
  }

  registration(to: RegistrationUserTO): Observable<any> {
    return this.httpClient.put(`${environment.backendUrl}/user/registration`, to);
  }
}
