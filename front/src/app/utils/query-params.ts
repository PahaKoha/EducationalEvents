import {BusinessFetchQueryParams} from '../transfer/params-for-search';

export function emptyQueryParams<F>(): BusinessFetchQueryParams<F> {
  return {
    filter: {} as F,
    pagination: {pageNo: 0, pageSize: 50},
    sorting: [],
  };
}
