import { Request, Response } from 'express';
import { DbContext } from '../../data-store/factories';

export default async (_: Request, res: Response) => {
  return res.json(await DbContext.waterCollectors.find({}));
}
