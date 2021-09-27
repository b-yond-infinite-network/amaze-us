import { DBQuery, QueryFilter } from "data-store/repository";

function translateFilter<T>(filter: QueryFilter<T>) {
  switch(filter.type) {
    case 'gt':
      return { [filter.field]: { $gt: filter.value } };
    case 'gte':
      return { [filter.field]: { $gte: filter.value } };
    case 'in':
      return { [filter.field]: { $in: filter.value } };
    case 'eq':
      return { [filter.field]: { $eq: filter.value } };
    case 'lt':
      return { [filter.field]: { $lt: filter.value } };
    case 'lte':
      return { [filter.field]: { $lt: filter.value } };
    default:
      throw new Error(`Unsupported filter type ${filter.type}`);
  }
}

export function translateDbQueryToLokiQuery<T>(dbQuery: DBQuery<T>) : LokiQuery<T & LokiObj> {
  const query: LokiQuery<T & LokiObj> = {};
  const filters = dbQuery.filters ?? [];
  const operator = dbQuery.operator ?? 'and';
  
  const translated = filters.reduce((lokiQuery: LokiQuery<T & LokiObj>, filter) => {
    lokiQuery[filter.field] = translateFilter(filter);
    return lokiQuery;
  }, {});

  if (operator === 'and') {
    query.$and = translated;
  } else if (operator === 'or') {
    query.$or = translated;
  }

  return query;
}