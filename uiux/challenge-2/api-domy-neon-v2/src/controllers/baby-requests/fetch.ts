import { Request, Response } from 'express';
import { DbContext } from '../../data-store/factories';

export default async (_: Request, res: Response) => {
  // DTO mapping is ommited for brievity - database entities are directly sent
  return res.json(await DbContext.babyMakingRequest.find({}));
}
