import { DBQuery, QueryFilter } from "data-store/repository";
import { BaseEntity } from "models/base-entity";

function translateFilter<T>(filter: QueryFilter<T>) {
  switch(filter.type) {
    case 'gt':
      return { $gt: filter.value };
    case 'gte':
      return { $gte: filter.value };
    case 'in':
      return { $in: filter.value };
    case 'eq':
      return { $eq: filter.value };
    case 'lt':
      return { $lt: filter.value };
    case 'lte':
      return { $lt: filter.value };
    default:
      throw new Error(`Unsupported filter type ${filter.type}`);
  }
}

export function isDBQuery<T extends BaseEntity>(query: any) : query is DBQuery<T> {
  return 'filters' in query;
}

export function translateEntityFilterToLokiQuery<T>(entity: Partial<T>) {
  const query: LokiQuery<T & LokiObj> = {};
  for(const field in entity) {
    query[field] = { $eq: entity[field] }
  }

  return query;
}

export function translateDbQueryToLokiQuery<T>(dbQuery: DBQuery<T>) : LokiQuery<T & LokiObj> {
  const query: LokiQuery<T & LokiObj> = {};
  const filters = dbQuery.filters ?? [];
  const operator = dbQuery.operator ?? 'and';
  
  const translated = filters.map(filter => ({ [filter.field]: translateFilter(filter) }));

  if (operator === 'and') {
    query.$and = translated;
  } else if (operator === 'or') {
    query.$or = translated;
  }

  return query;
}