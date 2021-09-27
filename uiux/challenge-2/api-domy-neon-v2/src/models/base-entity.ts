import { v4 as uuidv4 } from 'uuid';

export abstract class BaseEntity {
  id: string;
  constructor(id?: string) {
    this.id = id ? id : uuidv4();
  }
}