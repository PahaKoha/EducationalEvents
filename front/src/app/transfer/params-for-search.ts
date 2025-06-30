
export interface AdvancedFieldFilter<T> {
  type: 'EQUALS' | 'IN' | 'BETWEEN';
  singleValue?: T;
  multipleValue?: T[];
}

export interface EventFilter {
  eventType?:   AdvancedFieldFilter<number>;
  sphere?:      AdvancedFieldFilter<number>;
  isInternal?:  AdvancedFieldFilter<boolean>;
}


export interface Pagination {
  pageNo:   number;
  pageSize: number;
}

export interface Sorting {
  parameter: string;
  sortType:  'ASC' | 'ASC_ALTERNATE' | 'DESC' | 'DESC_ALTERNATE';
}


export interface BusinessFetchQueryParams<F> {
  filter:     F;
  pagination: Pagination;
  sorting:    Sorting[];
}
