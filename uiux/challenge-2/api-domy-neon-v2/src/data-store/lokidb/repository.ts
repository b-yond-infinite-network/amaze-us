import lokijs from 'lokijs';
import { BaseEntity } from 'models/base-entity';
import { DBQuery, IRepo } from '../repository';
import {
  translateDbQueryToLokiQuery,
  translateEntityFilterToLokiQuery,
} from './utils';

function isDBQuery<T>(query: any) : query is DBQuery<T> {
  return 'filters' in query;
}

export default class LokiDBRepo<T extends BaseEntity> implements IRepo<T> {
  constructor(
    private dbCollection: lokijs.Collection<T>
  ) { }

  find(query: DBQuery<T> | Partial<T>): Promise<T[]> {
    const lokiQuery = isDBQuery(query) ? translateDbQueryToLokiQuery(query as DBQuery<T>) :
      translateEntityFilterToLokiQuery(query as Partial<T>);

    return Promise.resolve(this.dbCollection.find(lokiQuery));
  }

  findOne(query: DBQuery<T> | Partial<T>): Promise<T> {
    const lokiQuery = isDBQuery(query) ? translateDbQueryToLokiQuery(query as DBQuery<T>) :
      translateEntityFilterToLokiQuery(query as Partial<T>);

    return Promise.resolve(this.dbCollection.findOne(lokiQuery));
  }

  getById(id: string) : Promise<T> {
    return Promise.resolve(this.dbCollection.findOne({
      id: { $eq: id }
    }));
  }

  getAll(limit?: number, offset?: number) : Promise<T[]> {
    return Promise.resolve(this.dbCollection.chain()
      .find({})
      .offset(offset ?? 0)
      .limit(limit ?? Number.MAX_SAFE_INTEGER)
      .data());
  }

  insert(entity: T): Promise<void> {
    this.dbCollection.insert(entity);
    return Promise.resolve();
  }

  update(entity: T): Promise<void> {
    this.dbCollection.findAndUpdate({
      id: { $eq: entity.id }
    }, _ => entity);
    return Promise.resolve();
  }

  remove(entity: T): Promise<void> {
    this.dbCollection.findAndRemove({
      id: { $eq: entity.id }
    });
    return Promise.resolve();
  }
}