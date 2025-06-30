import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../env/environment-local';
import {BusinessFetchQueryParams, EventFilter, Pagination, Sorting} from '../transfer/params-for-search';
import {EventProjection} from '../transfer/EventProjection';

function emptyParams(): BusinessFetchQueryParams<EventFilter> {
  return {
    filter: {eventType: undefined, sphere: undefined, isInternal: undefined},
    pagination: {pageNo: 0, pageSize: 50} as Pagination,
    sorting: [] as Sorting[],
  };
}

@Injectable({
  providedIn: 'root'
})
export class UserDashboardService {

  constructor(private http: HttpClient) {
  }

  /**
   * POST /events/search
   *
   * @param params  объект BusinessFetchQueryParams;
   *                если не передан ― используются «пустые» значения
   */
  fetchEvents(
    params: Partial<BusinessFetchQueryParams<EventFilter>> = {}
  ): Observable<EventProjection[]> {
    const body = {...emptyParams(), ...params} as BusinessFetchQueryParams<EventFilter>;

    return this.http.post<EventProjection[]>(
      `${environment.backendUrl}/event/search`,
      body,
      {withCredentials: true}          // если куки нужны
    );
  }
}
